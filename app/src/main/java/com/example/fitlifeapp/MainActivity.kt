package com.example.fitlifeapp

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
import com.example.fitlifeapp.screen.RutinaDetalleScreen
import com.example.fitlifeapp.screen.RutinasScreen
import com.example.fitlifeapp.viewmodel.RutinasViewModel

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
                            onOpenRutina = { currentScreen = "detalle" }
                        )
                        "detalle" -> RutinaDetalleScreen(
                            vm = vm
                        )
                    }
                }
            }
        }
    }
}