package com.example.fitnessapp.service;

import com.example.fitnessapp.model.Progreso;

import java.util.List;
public interface ProgresoService {
    List<Progreso> listarPorUsuario(Long idUsuario);
    Progreso guardar(Progreso p);
    Progreso actualizar(Long id, Progreso p);
    void eliminar(Long id);
}
