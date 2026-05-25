package com.example.fitlifeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitlifeapp.network.AlimentoDto
import com.example.fitlifeapp.network.AlimentoIdDto
import com.example.fitlifeapp.network.RegistroComidaCrearDto
import com.example.fitlifeapp.network.RegistroComidaDto
import com.example.fitlifeapp.network.UsuarioRefDto
import com.example.fitlifeapp.repository.FitnessRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class CaloriasViewModel(
    private val repository: FitnessRepository = FitnessRepository()
) : ViewModel() {

    private val _alimentos = MutableStateFlow<List<AlimentoDto>>(emptyList())
    val alimentos: StateFlow<List<AlimentoDto>> = _alimentos

    private val _comidasHoy = MutableStateFlow<List<RegistroComidaDto>>(emptyList())
    val comidasHoy: StateFlow<List<RegistroComidaDto>> = _comidasHoy

    private val _caloriasHoy = MutableStateFlow(0.0)
    val caloriasHoy: StateFlow<Double> = _caloriasHoy

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message

    private val _alimentoBuscado = MutableStateFlow("")
    val alimentoBuscado: StateFlow<String> = _alimentoBuscado

    val alimentosFiltrados: StateFlow<List<AlimentoDto>>
        get() = _alimentos

    fun setFiltro(texto: String) {
        _alimentoBuscado.value = texto
    }

    fun obtenerAlimentosFiltrados(): List<AlimentoDto> {
        val filtro = _alimentoBuscado.value.lowercase()
        return if (filtro.isBlank()) _alimentos.value
        else _alimentos.value.filter { it.nombre.lowercase().contains(filtro) }
    }

    fun cargarAlimentos() {
        viewModelScope.launch {
            _loading.value = true
            runCatching { repository.obtenerAlimentos() }
                .onSuccess { _alimentos.value = it }
                .onFailure { _message.value = "Error cargando alimentos: ${it.message}" }
            _loading.value = false
        }
    }

    fun cargarComidasHoy(idUsuario: Long) {
        val fecha = LocalDate.now().toString()
        viewModelScope.launch {
            _loading.value = true
            runCatching { repository.obtenerComidasPorFecha(idUsuario, fecha) }
                .onSuccess { _comidasHoy.value = it }
                .onFailure { _message.value = "Error cargando comidas: ${it.message}" }
            runCatching { repository.obtenerCaloriasDia(idUsuario, fecha) }
                .onSuccess { _caloriasHoy.value = it }
                .onFailure { }
            _loading.value = false
        }
    }

    fun registrarComida(idUsuario: Long, alimento: AlimentoDto, cantidad: Double) {
        viewModelScope.launch {
            _loading.value = true
            runCatching {
                repository.registrarComida(
                    RegistroComidaCrearDto(
                        usuario = UsuarioRefDto(idUsuario),
                        alimento = AlimentoIdDto(alimento.idAlimento),
                        cantidad = cantidad,
                        fecha = LocalDate.now().toString()
                    )
                )
            }.onSuccess {
                _message.value = "Comida registrada"
                cargarComidasHoy(idUsuario)
            }.onFailure { e ->
                _message.value = "Error registrando comida: ${e.message}"
            }
            _loading.value = false
        }
    }
}