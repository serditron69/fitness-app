package com.example.ftness_app

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface FitLifeApi {

    @GET("api/rutinas/usuario/{idUsuario}/activas")
    suspend fun getRutinasActivas(
        @Path("idUsuario") idUsuario: Long
    ): List<RutinaDto>

    @GET("api/rutina-ejercicios/rutina/{idRutina}")
    suspend fun getRutinaEjercicios(
        @Path("idRutina") idRutina: Long
    ): List<RutinaEjercicioDto>

    @PUT("api/rutina-ejercicios/{id}")
    suspend fun updateRutinaEjercicio(
        @Path("id") id: Long,
        @Body body: RutinaEjercicioDto
    ): RutinaEjercicioDto

    @POST("api/registro-entrenamientos")
    suspend fun crearRegistroEntrenamiento(
        @Body body: RegistroEntrenamientoDto
    ): RegistroEntrenamientoDto
}