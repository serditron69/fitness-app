package com.example.fitnessapp.service.impl;

import com.example.fitnessapp.model.RutinaEjercicio;
import com.example.fitnessapp.repository.RutinaEjercicioRepository;
import com.example.fitnessapp.service.RutinaEjercicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RutinaEjercicioServiceImpl implements RutinaEjercicioService {
    private final RutinaEjercicioRepository rutinaEjercicioRepository;

    @Override public List<RutinaEjercicio> listarPorRutina(Long id) { return rutinaEjercicioRepository.findByRutina_IdRutina(id); }
    @Override public RutinaEjercicio guardar(RutinaEjercicio re) { return rutinaEjercicioRepository.save(re); }
    @Override public void eliminar(Long id) { rutinaEjercicioRepository.deleteById(id); }

    @Override
    public RutinaEjercicio actualizar(Long id, RutinaEjercicio datos) {
        return rutinaEjercicioRepository.findById(id).map(re -> {
            re.setSeries(datos.getSeries());
            re.setRepeticiones(datos.getRepeticiones());
            re.setPesoKg(datos.getPesoKg());
            re.setDuracionSeg(datos.getDuracionSeg());
            re.setOrden(datos.getOrden());
            return rutinaEjercicioRepository.save(re);
        }).orElseThrow(() -> new RuntimeException("Registro no encontrado"));
    }
}
