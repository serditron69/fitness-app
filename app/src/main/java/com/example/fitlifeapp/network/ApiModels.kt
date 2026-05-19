package com.example.fitlifeapp.network

import kotlinx.serialization.Serializable

@Serializable
data class UsuarioDto(
    val idUsuario: Long? = null,
    val nombre: String = "",
    val email: String = "",
    val rol: String = "USER",
    val pesoKg: Double? = null,
    val alturaCm: Int? = null
)

@Serializable
data class EjercicioDto(
    val idEjercicio: Long? = null,
    val nombre: String = "",
    val grupoMuscular: String = "",
    val tipo: String = "",
    val descripcion: String? = null,
    val equipamiento: String? = null,
    val visible: Boolean = true
)

@Serializable
data class RutinaDto(
    val idRutina: Long? = null,
    val usuario: UsuarioDto? = null,
    val nombre: String = "",
    val descripcion: String? = null,
    val objetivo: String = "",
    val nivel: String = "",
    val activa: Boolean = true
)

@Serializable
data class RegistroDto(
    val idRegistro: Long? = null,
    val usuario: UsuarioDto? = null,
    val fecha: String = "",
    val duracionMin: Int? = null,
    val caloriasQuemadas: Int? = null,
    val estado: String = ""
)

@Serializable
data class AlimentoDto(
    val idAlimento: Long? = null,
    val nombre: String = "",
    val categoria: String = "",
    val calorias100g: Double = 0.0,
    val proteinas100g: Double? = 0.0,
    val carbohidratos100g: Double? = 0.0,
    val grasas100g: Double? = 0.0,
    val fibra100g: Double? = 0.0,
    val fuente: String? = "MANUAL",
    val visible: Boolean = true
)
