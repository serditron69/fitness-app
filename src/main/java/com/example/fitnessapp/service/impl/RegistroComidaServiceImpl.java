package com.example.fitnessapp.service.impl;

import com.example.fitnessapp.model.RegistroComida;
import com.example.fitnessapp.repository.RegistroComidaRepository;
import com.example.fitnessapp.service.RegistroComidaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegistroComidaServiceImpl implements RegistroComidaService {
    private final RegistroComidaRepository registroComidaRepository;

    @Override public List<RegistroComida> listarPorUsuario(Long id) { return registroComidaRepository.findByUsuario_IdUsuario(id); }
    @Override public List<RegistroComida> listarPorUsuarioYFecha(Long id, LocalDate fecha) { return registroComidaRepository.findByUsuario_IdUsuarioAndFecha(id, fecha); }
    @Override public Optional<RegistroComida> buscarPorId(Long id) { return registroComidaRepository.findById(id); }
    @Override public RegistroComida guardar(RegistroComida r) { return registroComidaRepository.save(r); }
    @Override public void eliminar(Long id) { registroComidaRepository.deleteById(id); }
    @Override public Double totalCaloriasDia(Long idUsuario, LocalDate fecha) { return registroComidaRepository.totalCaloriasDia(idUsuario, fecha); }

    @Override
    public RegistroComida actualizar(Long id, RegistroComida datos) {
        return registroComidaRepository.findById(id).map(r -> {
            r.setFecha(datos.getFecha());
            r.setTipoComida(datos.getTipoComida());
            r.setNotas(datos.getNotas());
            return registroComidaRepository.save(r);
        }).orElseThrow(() -> new RuntimeException("Registro de comida no encontrado"));
    }
}
