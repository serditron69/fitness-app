package com.example.fitlifeapp.repository

import com.example.fitlifeapp.network.AlimentoDto
import com.example.fitlifeapp.network.RetrofitClient

class FitnessRepository {
    private val api = RetrofitClient.api

    suspend fun usuarios() = api.getUsuarios()
    suspend fun ejercicios() = api.getEjercicios().filter { it.visible }
    suspend fun rutinas() = api.getRutinas().filter { it.activa }
    suspend fun registros() = api.getRegistros()
    suspend fun alimentos() = api.getAlimentos().filter { it.visible }
    suspend fun buscarAlimentos(nombre: String) = api.buscarAlimentos(nombre)
    suspend fun importarAlimento(alimento: AlimentoDto) = api.importarAlimento(alimento)
}
