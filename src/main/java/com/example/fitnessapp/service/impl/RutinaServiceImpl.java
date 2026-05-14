package com.example.fitnessapp.service.impl;

import com.example.fitnessapp.model.Rutina;
import com.example.fitnessapp.repository.RutinaRepository;
import com.example.fitnessapp.service.RutinaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RutinaServiceImpl implements RutinaService {
    private final RutinaRepository rutinaRepository;

    @Override public List<Rutina> listarTodas() { return rutinaRepository.findAll(); }
    @Override public Optional<Rutina> buscarPorId(Long id) { return rutinaRepository.findById(id); }
    @Override public List<Rutina> buscarPorUsuario(Long id) { return rutinaRepository.findByUsuario_IdUsuario(id); }
    @Override public List<Rutina> buscarActivas(Long id) { return rutinaRepository.findByUsuario_IdUsuarioAndActiva(id, true); }
    @Override public Rutina guardar(Rutina r) { return rutinaRepository.save(r); }
    @Override public void eliminar(Long id) { rutinaRepository.deleteById(id); }

    @Override
    public Rutina actualizar(Long id, Rutina datos) {
        return rutinaRepository.findById(id).map(r -> {
            r.setNombre(datos.getNombre());
            r.setDescripcion(datos.getDescripcion());
            r.setObjetivo(datos.getObjetivo());
            r.setNivel(datos.getNivel());
            r.setActiva(datos.getActiva());
            return rutinaRepository.save(r);
        }).orElseThrow(() -> new RuntimeException("Rutina no encontrada"));
    }
}
