package com.example.fitlifeapp.repository

import com.example.fitlifeapp.network.AlimentoDto
import com.example.fitlifeapp.network.FitLifeApi
import com.example.fitlifeapp.network.RegistroEntrenamientoDto
import com.example.fitlifeapp.network.RetrofitClient
import com.example.fitlifeapp.network.RutinaDto
import com.example.fitlifeapp.network.RutinaEjercicioDto

class FitnessRepository(
    private val api: FitLifeApi = RetrofitClient.api
) {

    suspend fun obtenerAlimentos(idUsuario: Long): List<AlimentoDto> {
        return api.obtenerAlimentos(idUsuario)
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
}