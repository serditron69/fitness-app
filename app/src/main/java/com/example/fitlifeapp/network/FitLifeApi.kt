package com.example.fitlifeapp.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FitLifeApi {
    @GET("api/usuarios")
    suspend fun getUsuarios(): List<UsuarioDto>

    @GET("api/ejercicios")
    suspend fun getEjercicios(): List<EjercicioDto>

    @GET("api/rutinas")
    suspend fun getRutinas(): List<RutinaDto>

    @GET("api/registros")
    suspend fun getRegistros(): List<RegistroDto>

    @GET("api/alimentos")
    suspend fun getAlimentos(): List<AlimentoDto>

    @GET("api/alimentos/open-food-facts/buscar")
    suspend fun buscarAlimentos(@Query("nombre") nombre: String): List<AlimentoDto>

    @POST("api/alimentos/open-food-facts/importar")
    suspend fun importarAlimento(@Body alimento: AlimentoDto): AlimentoDto

    @GET("api/registro-comidas/usuario/{id}")
    suspend fun getComidasUsuario(@Path("id") id: Long): List<Map<String, String>>
}
