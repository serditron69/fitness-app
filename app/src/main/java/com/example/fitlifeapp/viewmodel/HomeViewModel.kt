package com.example.ftness_app

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {
    private val _mensaje = MutableStateFlow("Bienvenido a FitLife")
    val mensaje: StateFlow<String> = _mensaje
}