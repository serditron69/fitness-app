package com.example.ftness_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface {
                    val vm: RutinasViewModel = viewModel()
                    var currentScreen by remember { mutableStateOf("rutinas") }

                    when (currentScreen) {
                        "rutinas" -> RutinasScreen(
                            vm = vm,
                            idUsuario = 1L,
                            onOpenRutina = { currentScreen = "detalle" }
                        )
                        "detalle" -> RutinaDetalleScreen(
                            vm = vm,
                            idUsuario = 1L
                        )
                    }
                }
            }
        }
    }
}