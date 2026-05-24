package com.example.ftness_app

class FitnessRepository {

    private val api = RetrofitClient.api

    suspend fun obtenerRutinasActivas(idUsuario: Long) =
        api.getRutinasActivas(idUsuario)

    suspend fun obtenerEjerciciosDeRutina(idRutina: Long) =
        api.getRutinaEjercicios(idRutina).sortedBy { it.orden ?: Int.MAX_VALUE }

    suspend fun actualizarRutinaEjercicio(item: RutinaEjercicioDto): RutinaEjercicioDto {
        return api.updateRutinaEjercicio(item.idRutinaEjercicio, item)
    }

    suspend fun registrarEntrenamiento(body: RegistroEntrenamientoDto) =
        api.crearRegistroEntrenamiento(body)
}