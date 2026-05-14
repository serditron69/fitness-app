package com.example.fitnessapp.service.impl;

import com.example.fitnessapp.model.Ejercicio;
import com.example.fitnessapp.repository.EjercicioRepository;
import com.example.fitnessapp.service.EjercicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EjercicioServiceImpl implements EjercicioService {
    private final EjercicioRepository ejercicioRepository;

    @Override public List<Ejercicio> listarTodos() { return ejercicioRepository.findAll(); }
    @Override public Optional<Ejercicio> buscarPorId(Long id) { return ejercicioRepository.findById(id); }
    @Override public List<Ejercicio> buscarPorGrupoMuscular(String g) { return ejercicioRepository.findByGrupoMuscular(g); }
    @Override public List<Ejercicio> buscarPorTipo(String t) { return ejercicioRepository.findByTipo(t); }
    @Override public Ejercicio guardar(Ejercicio e) { return ejercicioRepository.save(e); }
    @Override public void eliminar(Long id) { ejercicioRepository.deleteById(id); }

    @Override
    public Ejercicio actualizar(Long id, Ejercicio datos) {
        return ejercicioRepository.findById(id).map(e -> {
            e.setNombre(datos.getNombre());
            e.setGrupoMuscular(datos.getGrupoMuscular());
            e.setTipo(datos.getTipo());
            e.setDescripcion(datos.getDescripcion());
            e.setEquipamiento(datos.getEquipamiento());
            return ejercicioRepository.save(e);
        }).orElseThrow(() -> new RuntimeException("Ejercicio no encontrado"));
    }

    @Override
    public Ejercicio cambiarVisibilidad(Long id, Boolean visible) {
        return ejercicioRepository.findById(id).map(e -> {
            e.setVisible(visible);
            return ejercicioRepository.save(e);
        }).orElseThrow(() -> new RuntimeException("Ejercicio no encontrado"));
    }
}
