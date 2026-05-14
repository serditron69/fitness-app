package com.example.fitnessapp.repository;
import com.example.fitnessapp.model.Alimento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface AlimentoRepository extends JpaRepository<Alimento, Long> {
    List<Alimento> findByNombreContainingIgnoreCase(String nombre);
    List<Alimento> findByCategoria(String categoria);
    List<Alimento> findByVisible(Boolean visible);
}
