package com.example.fitlifeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitlifeapp.network.*
import com.example.fitlifeapp.repository.FitnessRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class RutinasViewModel(
    private val repository: FitnessRepository = FitnessRepository()
) : ViewModel() {

    private val _rutinas = MutableStateFlow<List<RutinaDto>>(emptyList())
    val rutinas: StateFlow<List<RutinaDto>> = _rutinas

    private val _rutinaSeleccionada = MutableStateFlow<RutinaDto?>(null)
    val rutinaSeleccionada: StateFlow<RutinaDto?> = _rutinaSeleccionada

    private val _ejerciciosRutina = MutableStateFlow<List<RutinaEjercicioDto>>(emptyList())
    val ejerciciosRutina: StateFlow<List<RutinaEjercicioDto>> = _ejerciciosRutina

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message

    fun cargarRutinas(idUsuario: Long) {
        viewModelScope.launch {
            _loading.value = true
            runCatching {
                repository.obtenerRutinasActivas(idUsuario)
            }.onSuccess {
                _rutinas.value = it
                _message.value = null
            }.onFailure { e ->
                _message.value = "Error cargando rutinas: ${e.message}"
            }
            _loading.value = false
        }
    }

    fun crearRutina(
        idUsuario: Long,
        nombre: String,
        descripcion: String,
        objetivo: String,
        nivel: String
    ) {
        viewModelScope.launch {
            _loading.value = true
            runCatching {
                repository.crearRutina(
                    RutinaCrearDto(
                        nombre = nombre,
                        descripcion = descripcion,
                        objetivo = objetivo,
                        nivel = nivel,
                        activa = true,
                        usuario = UsuarioRefDto(idUsuario)
                    )
                )
            }.onSuccess {
                _message.value = "Rutina creada"
                cargarRutinas(idUsuario)
            }.onFailure { e ->
                _message.value = "Error creando rutina: ${e.message}"
            }
            _loading.value = false
        }
    }

    fun seleccionarRutina(rutina: RutinaDto) {
        _rutinaSeleccionada.value = rutina
        cargarEjerciciosRutina(rutina.idRutina)
    }

    fun cargarEjerciciosRutina(idRutina: Long) {
        viewModelScope.launch {
            _loading.value = true
            runCatching {
                repository.obtenerEjerciciosDeRutina(idRutina)
            }.onSuccess {
                _ejerciciosRutina.value = it
                _message.value = null
            }.onFailure { e ->
                _message.value = "Error cargando ejercicios: ${e.message}"
            }
            _loading.value = false
        }
    }

    fun cambiarSeries(id: Long, nuevoValor: Int) {
        val actual = _ejerciciosRutina.value.find { it.idRutinaEjercicio == id } ?: return
        guardarCambio(actual.copy(series = nuevoValor.coerceAtLeast(1)))
    }

    fun cambiarRepeticiones(id: Long, nuevoValor: Int) {
        val actual = _ejerciciosRutina.value.find { it.idRutinaEjercicio == id } ?: return
        guardarCambio(actual.copy(repeticiones = nuevoValor.coerceAtLeast(1)))
    }

    fun cambiarPeso(id: Long, nuevoPeso: Double?) {
        val actual = _ejerciciosRutina.value.find { it.idRutinaEjercicio == id } ?: return
        guardarCambio(actual.copy(pesoKg = nuevoPeso))
    }

    private fun guardarCambio(item: RutinaEjercicioDto) {
        viewModelScope.launch {
            runCatching {
                repository.actualizarRutinaEjercicio(item.idRutinaEjercicio, item)
            }.onSuccess { actualizado ->
                _ejerciciosRutina.value = _ejerciciosRutina.value.map {
                    if (it.idRutinaEjercicio == actualizado.idRutinaEjercicio) actualizado else it
                }
                _message.value = "Ejercicio actualizado"
            }.onFailure { e ->
                _message.value = "Error: ${e.message}"
            }
        }
    }

    fun finalizarRutina(idUsuario: Long, duracionMin: Int, notas: String) {
        val rutina = _rutinaSeleccionada.value ?: return
        viewModelScope.launch {
            runCatching {
                repository.registrarEntrenamiento(
                    RegistroEntrenamientoDto(
                        usuario = UsuarioRefDto(idUsuario),
                        rutina = rutina,
                        fecha = LocalDate.now().toString(),
                        duracionMin = duracionMin,
                        notas = notas,
                        estado = "COMPLETADO"
                    )
                )
            }.onSuccess {
                _message.value = "Entrenamiento guardado"
            }.onFailure { e ->
                _message.value = "Error guardando: ${e.message}"
            }
        }
    }
}