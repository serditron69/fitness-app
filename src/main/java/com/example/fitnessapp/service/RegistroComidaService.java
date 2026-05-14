package com.example.fitnessapp.service;
import com.example.fitnessapp.model.RegistroComida;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
public interface RegistroComidaService {
    List<RegistroComida> listarPorUsuario(Long idUsuario);
    List<RegistroComida> listarPorUsuarioYFecha(Long idUsuario, LocalDate fecha);
    Optional<RegistroComida> buscarPorId(Long id);
    RegistroComida guardar(RegistroComida r);
    RegistroComida actualizar(Long id, RegistroComida r);
    void eliminar(Long id);
    Double totalCaloriasDia(Long idUsuario, LocalDate fecha);
}
