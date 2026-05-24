package com.example.fitlifeapp.repository

import com.example.fitlifeapp.network.AlimentoDto
import com.example.fitlifeapp.network.FitLifeApi
import com.example.fitlifeapp.network.RegistroEntrenamientoDto
import com.example.fitlifeapp.network.RetrofitClient
import com.example.fitlifeapp.network.RutinaDto
import com.example.fitlifeapp.network.RutinaEjercicioDto
import com.example.fitlifeapp.network.UsuarioDto

class FitnessRepository(
    private val api: FitLifeApi = RetrofitClient.api
) {

    suspend fun obtenerAlimentos(): List<AlimentoDto> {
        return api.obtenerAlimentos()
    }

    suspend fun obtenerRutinasActivas(idUsuario: Long): List<RutinaDto> {
        return api.obtenerRutinasActivas(idUsuario)
    }

    suspend fun obtenerEjerciciosDeRutina(idRutina: Long): List<RutinaEjercicioDto> {
        return api.obtenerEjerciciosDeRutina(idRutina)
    }

    suspend fun actualizarRutinaEjercicio(
        idRutinaEjercicio: Long,
        item: RutinaEjercicioDto
    ): RutinaEjercicioDto {
        return api.actualizarRutinaEjercicio(idRutinaEjercicio, item)
    }

    suspend fun registrarEntrenamiento(registro: RegistroEntrenamientoDto) {
        api.registrarEntrenamiento(registro)
    }

    suspend fun registrarUsuario(usuario: UsuarioDto): UsuarioDto {
        return api.registrarUsuario(usuario)
    }

    suspend fun login(email: String, password: String): UsuarioDto {
        return api.login(email, password)
    }
}