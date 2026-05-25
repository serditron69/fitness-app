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

class RegistroComidasViewModel(
    private val repository: FitnessRepository = FitnessRepository()
) : ViewModel() {

    private val _alimentos = MutableStateFlow<List<AlimentoDto>>(emptyList())
    val alimentos: StateFlow<List<AlimentoDto>> = _alimentos

    private val _selectedAlimento = MutableStateFlow<AlimentoDto?>(null)
    val selectedAlimento: StateFlow<AlimentoDto?> = _selectedAlimento

    private val _cantidadGramos = MutableStateFlow("")
    val cantidadGramos: StateFlow<String> = _cantidadGramos

    private val _comidaTipo = MutableStateFlow("ALMUERZO")
    val comidaTipo: StateFlow<String> = _comidaTipo

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message

    private val _lastRegistro = MutableStateFlow<RegistroComidaDto?>(null)
    val lastRegistro: StateFlow<RegistroComidaDto?> = _lastRegistro

    fun cargarAlimentos() {
        viewModelScope.launch {
            _loading.value = true
            runCatching { repository.obtenerAlimentos() }
                .onSuccess { _alimentos.value = it }
                .onFailure { _message.value = "Error cargando alimentos: ${it.message}" }
            _loading.value = false
        }
    }

    fun seleccionarAlimento(alimento: AlimentoDto) {
        _selectedAlimento.value = alimento
    }

    fun onCantidadChange(value: String) {
        _cantidadGramos.value = value
    }

    fun onComidaTipoChange(value: String) {
        _comidaTipo.value = value
    }

    fun registrarComida(idUsuario: Long) {
        val alimento = _selectedAlimento.value ?: run {
            _message.value = "Selecciona un alimento"
            return
        }

        val gramos = _cantidadGramos.value.toDoubleOrNull()
        if (gramos == null || gramos <= 0) {
            _message.value = "Ingresa una cantidad valida"
            return
        }

        viewModelScope.launch {
            _loading.value = true
            runCatching {
                repository.registrarComida(
                    RegistroComidaCrearDto(
                        usuario = UsuarioRefDto(idUsuario = idUsuario),
                        alimento = AlimentoIdDto(idAlimento = alimento.idAlimento),
                        cantidad = gramos,
                        fecha = LocalDate.now().toString()
                    )
                )
            }.onSuccess {
                _lastRegistro.value = it
                _message.value = "Comida registrada correctamente"
            }.onFailure {
                _message.value = "Error registrando comida: ${it.message}"
            }
            _loading.value = false
        }
    }
}