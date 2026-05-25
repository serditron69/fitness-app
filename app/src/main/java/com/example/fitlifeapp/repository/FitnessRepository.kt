package com.example.fitlifeapp.repository

import com.example.fitlifeapp.network.*

class FitnessRepository(
    private val api: FitLifeApi = RetrofitClient.api
) {

    suspend fun registrarUsuario(usuario: UsuarioDto): UsuarioDto =
        api.registrarUsuario(usuario)

    suspend fun login(email: String, password: String): UsuarioDto =
        api.login(email, password)

    suspend fun obtenerRutinasActivas(idUsuario: Long): List<RutinaDto> =
        api.obtenerRutinasActivas(idUsuario)

    suspend fun crearRutina(rutina: RutinaCrearDto): RutinaDto =
        api.crearRutina(rutina)

    suspend fun obtenerEjerciciosDeRutina(idRutina: Long): List<RutinaEjercicioDto> =
        api.obtenerEjerciciosDeRutina(idRutina)

    suspend fun actualizarRutinaEjercicio(
        idRutinaEjercicio: Long,
        item: RutinaEjercicioDto
    ): RutinaEjercicioDto =
        api.actualizarRutinaEjercicio(idRutinaEjercicio, item)

    suspend fun registrarEntrenamiento(registro: RegistroEntrenamientoDto) =
        api.registrarEntrenamiento(registro)

    suspend fun obtenerRegistrosEntrenamiento(idUsuario: Long): List<RegistroEntrenamientoDto> =
        api.obtenerRegistrosEntrenamiento(idUsuario)

    suspend fun obtenerAlimentos(): List<AlimentoDto> =
        api.obtenerAlimentos()

    suspend fun registrarComida(registro: RegistroComidaCrearDto): RegistroComidaDto =
        api.registrarComida(registro)

    suspend fun obtenerComidasPorFecha(
        idUsuario: Long,
        fecha: String
    ): List<RegistroComidaDto> =
        api.obtenerComidasPorFecha(idUsuario, fecha)

    suspend fun obtenerCaloriasDia(idUsuario: Long, fecha: String): Double =
        api.obtenerCaloriasDia(idUsuario, fecha)
}