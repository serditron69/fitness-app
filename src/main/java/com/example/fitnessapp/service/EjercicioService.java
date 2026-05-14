package com.example.fitnessapp.service;

import com.example.fitnessapp.model.Ejercicio;

import java.util.List;
import java.util.Optional;
public interface EjercicioService {
    List<Ejercicio> listarTodos();
    Optional<Ejercicio> buscarPorId(Long id);
    List<Ejercicio> buscarPorGrupoMuscular(String grupo);
    List<Ejercicio> buscarPorTipo(String tipo);
    Ejercicio guardar(Ejercicio ejercicio);
    Ejercicio actualizar(Long id, Ejercicio ejercicio);
    void eliminar(Long id);
    Ejercicio cambiarVisibilidad(Long id, Boolean visible);
}
