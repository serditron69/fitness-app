package com.example.fitnessapp.service.impl;

import com.example.fitnessapp.model.Progreso;
import com.example.fitnessapp.repository.ProgresoRepository;
import com.example.fitnessapp.service.ProgresoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgresoServiceImpl implements ProgresoService {
    private final ProgresoRepository progresoRepository;

    @Override public List<Progreso> listarPorUsuario(Long id) { return progresoRepository.findByUsuario_IdUsuarioOrderByFechaAsc(id); }
    @Override public Progreso guardar(Progreso p) { return progresoRepository.save(p); }
    @Override public void eliminar(Long id) { progresoRepository.deleteById(id); }

    @Override
    public Progreso actualizar(Long id, Progreso datos) {
        return progresoRepository.findById(id).map(p -> {
            p.setPesoKg(datos.getPesoKg());
            p.setGrasaCorporalPct(datos.getGrasaCorporalPct());
            p.setMasaMuscularKg(datos.getMasaMuscularKg());
            p.setImc(datos.getImc());
            p.setNotas(datos.getNotas());
            return progresoRepository.save(p);
        }).orElseThrow(() -> new RuntimeException("Progreso no encontrado"));
    }
}
