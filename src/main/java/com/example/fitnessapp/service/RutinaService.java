package com.example.fitnessapp.service;

import com.example.fitnessapp.model.Rutina;

import java.util.List;
import java.util.Optional;
public interface RutinaService {
    List<Rutina> listarTodas();
    Optional<Rutina> buscarPorId(Long id);
    List<Rutina> buscarPorUsuario(Long idUsuario);
    List<Rutina> buscarActivas(Long idUsuario);
    Rutina guardar(Rutina rutina);
    Rutina actualizar(Long id, Rutina rutina);
    void eliminar(Long id);
}
