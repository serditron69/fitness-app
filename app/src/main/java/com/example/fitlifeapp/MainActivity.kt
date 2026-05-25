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
import com.example.fitlifeapp.screen.CaloriasScreen
import com.example.fitlifeapp.screen.CrearRutinaScreen
import com.example.fitlifeapp.screen.LoginScreen
import com.example.fitlifeapp.screen.RegistroScreen
import com.example.fitlifeapp.screen.RutinaDetalleScreen
import com.example.fitlifeapp.screen.RutinasScreen
import com.example.fitlifeapp.ui.theme.FitLifeAppTheme
import com.example.fitlifeapp.viewmodel.AuthViewModel
import com.example.fitlifeapp.viewmodel.CaloriasViewModel
import com.example.fitlifeapp.viewmodel.RutinasViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FitLifeAppTheme {
                Surface {
                    val authVm: AuthViewModel = viewModel()
                    val rutinasVm: RutinasViewModel = viewModel()
                    val caloriasVm: CaloriasViewModel = viewModel()

                    var currentScreen by remember { mutableStateOf("login") }
                    var idUsuario by remember { mutableLongStateOf(0L) }

                    val usuario = authVm.usuario

                    when (currentScreen) {
                        "login" -> LoginScreen(
                            vm = authVm,
                            onLoginSuccess = {
                                idUsuario = authVm.usuario.value?.idUsuario ?: 0L
                                currentScreen = "rutinas"
                            },
                            onGoToRegister = { currentScreen = "registro" }
                        )

                        "registro" -> RegistroScreen(
                            vm = authVm,
                            onRegistroSuccess = {
                                idUsuario = authVm.usuario.value?.idUsuario ?: 0L
                                currentScreen = "rutinas"
                            },
                            onGoToLogin = { currentScreen = "login" }
                        )

                        "rutinas" -> RutinasScreen(
                            vm = rutinasVm,
                            idUsuario = idUsuario,
                            onOpenRutina = { currentScreen = "detalle" },
                            onCrearRutina = { currentScreen = "crearRutina" },
                            onVerCalorias = { currentScreen = "calorias" }
                        )

                        "crearRutina" -> CrearRutinaScreen(
                            vm = rutinasVm,
                            idUsuario = idUsuario
                        )

                        "detalle" -> RutinaDetalleScreen(
                            vm = rutinasVm,
                            idUsuario = idUsuario,
                            onVolver = { currentScreen = "rutinas" }
                        )

                        "calorias" -> CaloriasScreen(
                            vm = caloriasVm,
                            idUsuario = idUsuario,
                            onVolver = { currentScreen = "rutinas" }
                        )
                    }
                }
            }
        }
    }
}