package com.example.fitlifeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitlifeapp.network.AlimentoDto
import com.example.fitlifeapp.network.EjercicioDto
import com.example.fitlifeapp.network.RutinaDto
import com.example.fitlifeapp.repository.FitnessRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class HomeUiState(
    val loading: Boolean = true,
    val caloriasConsumidas: Int = 1240,
    val metaCalorias: Int = 2000,
    val aguaVasos: Int = 5,
    val ejercicios: List<EjercicioDto> = emptyList(),
    val rutinas: List<RutinaDto> = emptyList(),
    val alimentos: List<AlimentoDto> = emptyList(),
    val buscados: List<AlimentoDto> = emptyList(),
    val error: String? = null
)

class HomeViewModel(
    private val repository: FitnessRepository = FitnessRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        cargarTodo()
    }

    fun cargarTodo() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(loading = true, error = null)

            runCatching {
                Triple(repository.ejercicios(), repository.rutinas(), repository.alimentos())
            }.onSuccess { data ->
                _uiState.value = _uiState.value.copy(
                    loading = false,
                    ejercicios = if (data.first.isNotEmpty()) data.first else ejerciciosDemo(),
                    rutinas = if (data.second.isNotEmpty()) data.second else rutinasDemo(),
                    alimentos = if (data.third.isNotEmpty()) data.third.take(8) else alimentosDemo(),
                    error = null
                )
            }.onFailure {
                _uiState.value = _uiState.value.copy(
                    loading = false,
                    ejercicios = ejerciciosDemo(),
                    rutinas = rutinasDemo(),
                    alimentos = alimentosDemo(),
                    error = "Modo demo: backend no disponible"
                )
            }
        }
    }

    fun buscarAlimentos(nombre: String) {
        if (nombre.isBlank()) return

        viewModelScope.launch {
            runCatching { repository.buscarAlimentos(nombre) }
                .onSuccess {
                    _uiState.value = _uiState.value.copy(
                        buscados = it,
                        error = null
                    )
                }
                .onFailure {
                    val resultadosLocales = alimentosDemo().filter { alimento ->
                        alimento.nombre.contains(nombre, ignoreCase = true) ||
                                alimento.categoria.contains(nombre, ignoreCase = true)
                    }

                    _uiState.value = _uiState.value.copy(
                        buscados = resultadosLocales,
                        error = "Búsqueda en modo demo"
                    )
                }
        }
    }

    fun importarAlimento(alimento: AlimentoDto) {
        viewModelScope.launch {
            runCatching { repository.importarAlimento(alimento) }
                .onSuccess {
                    _uiState.value = _uiState.value.copy(
                        buscados = emptyList(),
                        error = null
                    )
                    cargarTodo()
                }
                .onFailure {
                    val listaActual = _uiState.value.alimentos.toMutableList()
                    listaActual.add(0, alimento)

                    _uiState.value = _uiState.value.copy(
                        alimentos = listaActual.distinctBy { it.nombre },
                        buscados = emptyList(),
                        error = "Alimento agregado en modo demo"
                    )
                }
        }
    }

    private fun ejerciciosDemo(): List<EjercicioDto> {
        return listOf(
            EjercicioDto(
                idEjercicio = 1,
                nombre = "Press de banca",
                grupoMuscular = "Pecho",
                tipo = "Fuerza",
                descripcion = "Ejercicio compuesto para desarrollar pecho, hombros y tríceps.",
                equipamiento = "Barra"
            ),
            EjercicioDto(
                idEjercicio = 2,
                nombre = "Sentadilla",
                grupoMuscular = "Piernas",
                tipo = "Fuerza",
                descripcion = "Ejercicio clave para trabajar piernas y glúteos.",
                equipamiento = "Barra"
            ),
            EjercicioDto(
                idEjercicio = 3,
                nombre = "Peso muerto",
                grupoMuscular = "Espalda",
                tipo = "Fuerza",
                descripcion = "Movimiento compuesto para espalda baja, glúteos e isquiotibiales.",
                equipamiento = "Barra"
            ),
            EjercicioDto(
                idEjercicio = 4,
                nombre = "Dominadas",
                grupoMuscular = "Espalda",
                tipo = "Calistenia",
                descripcion = "Excelente ejercicio para dorsales y bíceps.",
                equipamiento = "Barra fija"
            ),
            EjercicioDto(
                idEjercicio = 5,
                nombre = "Plancha",
                grupoMuscular = "Core",
                tipo = "Resistencia",
                descripcion = "Ejercicio isométrico para fortalecer abdomen y zona media.",
                equipamiento = "Sin equipo"
            ),
            EjercicioDto(
                idEjercicio = 6,
                nombre = "Burpees",
                grupoMuscular = "Full Body",
                tipo = "Cardio",
                descripcion = "Ejercicio intenso para mejorar resistencia y quemar calorías.",
                equipamiento = "Sin equipo"
            )
        )
    }

    private fun rutinasDemo(): List<RutinaDto> {
        return listOf(
            RutinaDto(
                idRutina = 1,
                nombre = "Push Day",
                descripcion = "Pecho, hombro y tríceps para ganar fuerza.",
                objetivo = "Hipertrofia",
                nivel = "Intermedio",
                activa = true
            ),
            RutinaDto(
                idRutina = 2,
                nombre = "Pierna completa",
                descripcion = "Rutina enfocada en fuerza y resistencia de tren inferior.",
                objetivo = "Fuerza",
                nivel = "Intermedio",
                activa = true
            ),
            RutinaDto(
                idRutina = 3,
                nombre = "Cardio express",
                descripcion = "Sesión corta para quemar calorías en casa.",
                objetivo = "Pérdida de grasa",
                nivel = "Principiante",
                activa = true
            ),
            RutinaDto(
                idRutina = 4,
                nombre = "Core diario",
                descripcion = "Bloque rápido para abdomen y estabilidad.",
                objetivo = "Resistencia",
                nivel = "Principiante",
                activa = true
            )
        )
    }

    private fun alimentosDemo(): List<AlimentoDto> {
        return listOf(
            AlimentoDto(
                idAlimento = 1,
                nombre = "Pechuga de pollo",
                categoria = "Proteína",
                calorias100g = 165.0,
                proteinas100g = 31.0,
                carbohidratos100g = 0.0,
                grasas100g = 3.6
            ),
            AlimentoDto(
                idAlimento = 2,
                nombre = "Arroz blanco",
                categoria = "Carbohidrato",
                calorias100g = 130.0,
                proteinas100g = 2.7,
                carbohidratos100g = 28.0,
                grasas100g = 0.3
            ),
            AlimentoDto(
                idAlimento = 3,
                nombre = "Huevo",
                categoria = "Proteína",
                calorias100g = 155.0,
                proteinas100g = 13.0,
                carbohidratos100g = 1.1,
                grasas100g = 11.0
            ),
            AlimentoDto(
                idAlimento = 4,
                nombre = "Avena",
                categoria = "Desayuno",
                calorias100g = 389.0,
                proteinas100g = 16.9,
                carbohidratos100g = 66.3,
                grasas100g = 6.9
            ),
            AlimentoDto(
                idAlimento = 5,
                nombre = "Banano",
                categoria = "Fruta",
                calorias100g = 89.0,
                proteinas100g = 1.1,
                carbohidratos100g = 22.8,
                grasas100g = 0.3
            ),
            AlimentoDto(
                idAlimento = 6,
                nombre = "Yogurt griego",
                categoria = "Snack",
                calorias100g = 59.0,
                proteinas100g = 10.0,
                carbohidratos100g = 3.6,
                grasas100g = 0.4
            )
        )
    }
}