package com.example.fitnessapp.repository;

import com.example.fitnessapp.model.Rutina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface RutinaRepository extends JpaRepository<Rutina, Long> {
    List<Rutina> findByUsuario_IdUsuario(Long idUsuario);
    List<Rutina> findByNivel(String nivel);
    List<Rutina> findByUsuario_IdUsuarioAndActiva(Long idUsuario, Boolean activa);
}
