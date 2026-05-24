package com.example.fitlifeapp.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface FitLifeApi {

    @GET("alimentos/usuario/{idUsuario}")
    suspend fun obtenerAlimentos(
        @Path("idUsuario") idUsuario: Long
    ): List<AlimentoDto>

    @GET("rutinas/activas/{idUsuario}")
    suspend fun obtenerRutinasActivas(
        @Path("idUsuario") idUsuario: Long
    ): List<RutinaDto>

    @GET("rutinas/{idRutina}/ejercicios")
    suspend fun obtenerEjerciciosDeRutina(
        @Path("idRutina") idRutina: Long
    ): List<RutinaEjercicioDto>

    @PUT("rutina-ejercicios/{idRutinaEjercicio}")
    suspend fun actualizarRutinaEjercicio(
        @Path("idRutinaEjercicio") idRutinaEjercicio: Long,
        @Body item: RutinaEjercicioDto
    ): RutinaEjercicioDto

    @POST("entrenamientos")
    suspend fun registrarEntrenamiento(
        @Body registro: RegistroEntrenamientoDto
    )
}