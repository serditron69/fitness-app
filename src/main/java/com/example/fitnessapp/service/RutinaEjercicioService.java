package com.example.fitnessapp.service;

import com.example.fitnessapp.model.RutinaEjercicio;

import java.util.List;
public interface RutinaEjercicioService {
    List<RutinaEjercicio> listarPorRutina(Long idRutina);
    RutinaEjercicio guardar(RutinaEjercicio re);
    RutinaEjercicio actualizar(Long id, RutinaEjercicio re);
    void eliminar(Long id);
}
