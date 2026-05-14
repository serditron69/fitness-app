package com.example.fitnessapp.repository;

import com.example.fitnessapp.model.Ejercicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface EjercicioRepository extends JpaRepository<Ejercicio, Long> {
    List<Ejercicio> findByGrupoMuscular(String grupoMuscular);
    List<Ejercicio> findByTipo(String tipo);
    List<Ejercicio> findByVisible(Boolean visible);
}
