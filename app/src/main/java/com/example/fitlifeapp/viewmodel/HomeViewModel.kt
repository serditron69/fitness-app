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

    init {
        cargarAlimentos()
    }

    fun cargarAlimentos() {
        viewModelScope.launch {
            runCatching {
                repository.obtenerAlimentos()
            }.onSuccess {
                _alimentos.value = it
            }.onFailure {
                _alimentos.value = emptyList()
            }
        }
    }
}