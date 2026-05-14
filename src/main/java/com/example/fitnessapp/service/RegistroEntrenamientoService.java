package com.example.fitnessapp.service;

import com.example.fitnessapp.model.RegistroEntrenamiento;

import java.util.List;
public interface RegistroEntrenamientoService {
    List<RegistroEntrenamiento> listarTodos();
    List<RegistroEntrenamiento> listarPorUsuario(Long idUsuario);
    RegistroEntrenamiento guardar(RegistroEntrenamiento r);
    RegistroEntrenamiento actualizar(Long id, RegistroEntrenamiento r);
    void eliminar(Long id);
    Integer totalCalorias(Long idUsuario);
    Long entrenamientosMes(Long idUsuario, int mes, int anio);
}
