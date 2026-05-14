package com.example.fitnessapp.repository;

import com.example.fitnessapp.model.RegistroEntrenamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
public interface RegistroEntrenamientoRepository extends JpaRepository<RegistroEntrenamiento, Long> {
    List<RegistroEntrenamiento> findByUsuario_IdUsuario(Long idUsuario);

    @Query("SELECT SUM(r.caloriasQuemadas) FROM RegistroEntrenamiento r WHERE r.usuario.idUsuario = :idUsuario")
    Integer totalCaloriasByUsuario(Long idUsuario);

    @Query("SELECT COUNT(r) FROM RegistroEntrenamiento r WHERE r.usuario.idUsuario = :idUsuario AND MONTH(r.fecha) = :mes AND YEAR(r.fecha) = :anio")
    Long entrenamientosMes(Long idUsuario, int mes, int anio);
}
