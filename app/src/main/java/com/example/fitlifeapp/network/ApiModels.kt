package com.example.fitlifeapp.network

data class AlimentoDto(
    val idAlimento: Long = 0,
    val nombre: String,
    val calorias: Double,
    val proteinas: Double,
    val carbohidratos: Double,
    val grasas: Double
)

data class EjercicioDto(
    val idEjercicio: Long,
    val nombre: String,
    val grupoMuscular: String,
    val tipo: String,
    val descripcion: String? = null,
    val equipamiento: String? = null
)

data class RutinaDto(
    val idRutina: Long = 0,
    val nombre: String,
    val descripcion: String? = null,
    val objetivo: String,
    val nivel: String
)

data class RutinaCrearDto(
    val nombre: String,
    val descripcion: String?,
    val objetivo: String,
    val nivel: String,
    val activa: Boolean = true,
    val usuario: UsuarioRefDto
)

data class RutinaEjercicioDto(
    val idRutinaEjercicio: Long,
    val rutina: RutinaDto? = null,
    val ejercicio: EjercicioDto? = null,
    val series: Int,
    val repeticiones: Int,
    val pesoKg: Double? = null
)

data class UsuarioRefDto(
    val idUsuario: Long
)

data class RegistroEntrenamientoDto(
    val usuario: UsuarioRefDto,
    val rutina: RutinaDto,
    val fecha: String,
    val duracionMin: Int,
    val notas: String,
    val estado: String
)

data class UsuarioDto(
    val idUsuario: Long = 0,
    val nombre: String,
    val email: String,
    val password: String
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class RegistroComidaDto(
    val idRegistroComida: Long = 0,
    val usuario: UsuarioRefDto,
    val alimento: AlimentoDto,
    val cantidad: Double,
    val fecha: String
)

data class RegistroComidaCrearDto(
    val usuario: UsuarioRefDto,
    val alimento: AlimentoIdDto,
    val cantidad: Double,
    val fecha: String
)

data class AlimentoIdDto(
    val idAlimento: Long
)