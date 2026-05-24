package com.example.fitlifeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitlifeapp.network.UsuarioDto
import com.example.fitlifeapp.repository.FitnessRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: FitnessRepository = FitnessRepository()
) : ViewModel() {

    private val _usuario = MutableStateFlow<UsuarioDto?>(null)
    val usuario: StateFlow<UsuarioDto?> = _usuario

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _loginExitoso = MutableStateFlow(false)
    val loginExitoso: StateFlow<Boolean> = _loginExitoso

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            runCatching {
                repository.login(email, password)
            }.onSuccess { user ->
                _usuario.value = user
                _loginExitoso.value = true
            }.onFailure {
                _error.value = "Email o contraseña incorrectos"
            }
            _loading.value = false
        }
    }

    fun registrar(nombre: String, email: String, password: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            runCatching {
                repository.registrarUsuario(UsuarioDto(nombre = nombre, email = email, password = password))
            }.onSuccess { user ->
                _usuario.value = user
                _loginExitoso.value = true
            }.onFailure {
                _error.value = "Error al registrarse, intenta de nuevo"
            }
            _loading.value = false
        }
    }

    fun resetLoginExitoso() {
        _loginExitoso.value = false
    }
}