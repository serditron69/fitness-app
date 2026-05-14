package com.example.fitnessapp.repository;

import com.example.fitnessapp.model.RutinaEjercicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface RutinaEjercicioRepository extends JpaRepository<RutinaEjercicio, Long> {
    List<RutinaEjercicio> findByRutina_IdRutina(Long idRutina);
}
