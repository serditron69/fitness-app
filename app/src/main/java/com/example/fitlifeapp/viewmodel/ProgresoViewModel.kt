package com.example.fitlifeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitlifeapp.network.RegistroEntrenamientoDto
import com.example.fitlifeapp.repository.FitnessRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProgresoViewModel(
    private val repository: FitnessRepository = FitnessRepository()
) : ViewModel() {

    private val _registros = MutableStateFlow<List<RegistroEntrenamientoDto>>(emptyList())
    val registros: StateFlow<List<RegistroEntrenamientoDto>> = _registros

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message

    fun cargarProgreso(idUsuario: Long) {
        viewModelScope.launch {
            _loading.value = true
            runCatching {
                repository.obtenerRegistrosEntrenamiento(idUsuario)
            }.onSuccess { lista ->
                _registros.value = lista
                _message.value = "Registros cargados: ${lista.size}"
            }.onFailure { e ->
                _message.value = "Error cargando progreso: ${e.message}"
            }
            _loading.value = false
        }
    }
}