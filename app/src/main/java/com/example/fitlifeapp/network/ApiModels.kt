package com.example.fitlifeapp.network

data class AlimentoDto(
    val idAlimento: Long,
    val nombre: String,
    val calorias: Int? = null,
    val proteinas: Double? = null,
    val carbohidratos: Double? = null,
    val grasas: Double? = null,
    val porcion: String? = null
)

data class RutinaDto(
    val idRutina: Long,
    val nombre: String,
    val descripcion: String? = null,
    val objetivo: String,
    val nivel: String,
    val activa: Boolean
)

data class EjercicioDto(
    val idEjercicio: Long,
    val nombre: String,
    val grupoMuscular: String,
    val tipo: String,
    val descripcion: String? = null,
    val equipamiento: String? = null
)

data class RutinaEjercicioDto(
    val idRutinaEjercicio: Long,
    val rutina: RutinaDto? = null,
    val ejercicio: EjercicioDto? = null,
    val series: Int,
    val repeticiones: Int,
    val pesoKg: Double? = null,
    val duracionSeg: Int? = null,
    val tiempoDescansoSeg: Int? = null,
    val orden: Int? = null
)

data class UsuarioRefDto(
    val idUsuario: Long
)

data class RegistroEntrenamientoDto(
    val idRegistro: Long? = null,
    val usuario: UsuarioRefDto? = null,
    val rutina: RutinaDto? = null,
    val fecha: String,
    val duracionMin: Int? = null,
    val caloriasQuemadas: Int? = null,
    val notas: String? = null,
    val estado: String = "COMPLETADO"
)