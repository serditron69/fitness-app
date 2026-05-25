package com.example.fitlifeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fitlifeapp.screen.CrearRutinaScreen
import com.example.fitlifeapp.screen.LoginScreen
import com.example.fitlifeapp.screen.RegistroScreen
import com.example.fitlifeapp.screen.RutinaDetalleScreen
import com.example.fitlifeapp.screen.RutinasScreen
import com.example.fitlifeapp.ui.theme.FitLifeAppTheme
import com.example.fitlifeapp.viewmodel.AuthViewModel
import com.example.fitlifeapp.viewmodel.RutinasViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FitLifeAppTheme {
                Surface {
                    val authVm: AuthViewModel = viewModel()
                    val rutinasVm: RutinasViewModel = viewModel()
                    var currentScreen by remember { mutableStateOf("login") }
                    var idUsuario by remember { mutableLongStateOf(0L) }

                    when (currentScreen) {
                        "login" -> LoginScreen(
                            vm = authVm,
                            onLoginExitoso = { id ->
                                idUsuario = id
                                currentScreen = "rutinas"
                            },
                            onIrARegistro = { currentScreen = "registro" }
                        )
                        "registro" -> RegistroScreen(
                            vm = authVm,
                            onRegistroExitoso = { id ->
                                idUsuario = id
                                currentScreen = "rutinas"
                            },
                            onIrALogin = { currentScreen = "login" }
                        )
                        "rutinas" -> RutinasScreen(
                            vm = rutinasVm,
                            idUsuario = idUsuario,
                            onOpenRutina = { currentScreen = "detalle" },
                            onCrearRutina = { currentScreen = "crearRutina" }
                        )
                        "crearRutina" -> CrearRutinaScreen(
                            vm = rutinasVm,
                            idUsuario = idUsuario,
                            onRutinaCreada = { currentScreen = "rutinas" },
                            onVolver = { currentScreen = "rutinas" }
                        )
                        "detalle" -> RutinaDetalleScreen(
                            vm = rutinasVm,
                            idUsuario = idUsuario
                        )
                    }
                }
            }
        }
    }
}