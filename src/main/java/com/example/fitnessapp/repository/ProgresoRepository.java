package com.example.fitnessapp.repository;

import com.example.fitnessapp.model.Progreso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface ProgresoRepository extends JpaRepository<Progreso, Long> {
    List<Progreso> findByUsuario_IdUsuarioOrderByFechaAsc(Long idUsuario);
}
