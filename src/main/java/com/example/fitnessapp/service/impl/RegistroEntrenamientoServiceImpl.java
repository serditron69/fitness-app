package com.example.fitnessapp.service.impl;

import com.example.fitnessapp.model.RegistroEntrenamiento;
import com.example.fitnessapp.repository.RegistroEntrenamientoRepository;
import com.example.fitnessapp.service.RegistroEntrenamientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistroEntrenamientoServiceImpl implements RegistroEntrenamientoService {
    private final RegistroEntrenamientoRepository registroRepository;

    @Override public List<RegistroEntrenamiento> listarTodos() { return registroRepository.findAll(); }
    @Override public List<RegistroEntrenamiento> listarPorUsuario(Long id) { return registroRepository.findByUsuario_IdUsuario(id); }
    @Override public RegistroEntrenamiento guardar(RegistroEntrenamiento r) { return registroRepository.save(r); }
    @Override public void eliminar(Long id) { registroRepository.deleteById(id); }
    @Override public Integer totalCalorias(Long id) { return registroRepository.totalCaloriasByUsuario(id); }
    @Override public Long entrenamientosMes(Long id, int mes, int anio) { return registroRepository.entrenamientosMes(id, mes, anio); }

    @Override
    public RegistroEntrenamiento actualizar(Long id, RegistroEntrenamiento datos) {
        return registroRepository.findById(id).map(r -> {
            r.setFecha(datos.getFecha());
            r.setDuracionMin(datos.getDuracionMin());
            r.setCaloriasQuemadas(datos.getCaloriasQuemadas());
            r.setNotas(datos.getNotas());
            r.setEstado(datos.getEstado());
            return registroRepository.save(r);
        }).orElseThrow(() -> new RuntimeException("Registro no encontrado"));
    }
}
