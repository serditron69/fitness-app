package com.example.fitnessapp.repository;
import com.example.fitnessapp.model.DetalleComida;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface DetalleComidaRepository extends JpaRepository<DetalleComida, Long> {
    List<DetalleComida> findByRegistroComida_IdRegistroComida(Long idRegistroComida);
}
