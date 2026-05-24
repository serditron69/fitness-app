package com.example.fitlifeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitlifeapp.network.AlimentoDto
import com.example.fitlifeapp.repository.FitnessRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: FitnessRepository = FitnessRepository()
) : ViewModel() {

    private val _alimentos = MutableStateFlow<List<AlimentoDto>>(emptyList())
    val alimentos: StateFlow<List<AlimentoDto>> = _alimentos

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message

    fun cargarAlimentos(idUsuario: Long) {
        viewModelScope.launch {
            _loading.value = true
            runCatching {
                repository.obtenerAlimentos(idUsuario)
            }.onSuccess { lista ->
                _alimentos.value = lista
                _message.value = "Alimentos recibidos: ${lista.size}"
            }.onFailure { e ->
                _message.value = "Error cargando alimentos: ${e.message}"
            }
            _loading.value = false
        }
    }
}