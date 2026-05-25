package com.example.fitlifeapp.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface FitLifeApi {

    @POST("api/usuarios")
    suspend fun registrarUsuario(@Body usuario: UsuarioDto): UsuarioDto

    @POST("api/usuarios/login")
    suspend fun login(
        @Query("email") email: String,
        @Query("password") password: String
    ): UsuarioDto

    @GET("api/rutinas/usuario/{idUsuario}/activas")
    suspend fun obtenerRutinasActivas(
        @Path("idUsuario") idUsuario: Long
    ): List<RutinaDto>

    @POST("api/rutinas")
    suspend fun crearRutina(@Body rutina: RutinaCrearDto): RutinaDto

    @GET("api/rutina-ejercicios/rutina/{idRutina}")
    suspend fun obtenerEjerciciosDeRutina(
        @Path("idRutina") idRutina: Long
    ): List<RutinaEjercicioDto>

    @PUT("api/rutina-ejercicios/{idRutinaEjercicio}")
    suspend fun actualizarRutinaEjercicio(
        @Path("idRutinaEjercicio") idRutinaEjercicio: Long,
        @Body item: RutinaEjercicioDto
    ): RutinaEjercicioDto

    @POST("api/registros")
    suspend fun registrarEntrenamiento(@Body registro: RegistroEntrenamientoDto)

    @GET("api/registros/usuario/{idUsuario}")
    suspend fun obtenerRegistrosEntrenamiento(
        @Path("idUsuario") idUsuario: Long
    ): List<RegistroEntrenamientoDto>

    @GET("api/alimentos")
    suspend fun obtenerAlimentos(): List<AlimentoDto>

    @POST("api/registro-comidas")
    suspend fun registrarComida(@Body registro: RegistroComidaCrearDto): RegistroComidaDto

    @GET("api/registro-comidas/usuario/{idUsuario}/fecha/{fecha}")
    suspend fun obtenerComidasPorFecha(
        @Path("idUsuario") idUsuario: Long,
        @Path("fecha") fecha: String
    ): List<RegistroComidaDto>

    @GET("api/registro-comidas/usuario/{idUsuario}/calorias-dia/{fecha}")
    suspend fun obtenerCaloriasDia(
        @Path("idUsuario") idUsuario: Long,
        @Path("fecha") fecha: String
    ): Double
}