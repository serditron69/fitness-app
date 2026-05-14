package com.example.fitnessapp.repository;
import com.example.fitnessapp.model.RegistroComida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.List;
public interface RegistroComidaRepository extends JpaRepository<RegistroComida, Long> {
    List<RegistroComida> findByUsuario_IdUsuario(Long idUsuario);
    List<RegistroComida> findByUsuario_IdUsuarioAndFecha(Long idUsuario, LocalDate fecha);

    @Query("SELECT COALESCE(SUM(d.caloriasConsumidas), 0) FROM DetalleComida d WHERE d.registroComida.usuario.idUsuario = :idUsuario AND d.registroComida.fecha = :fecha")
    Double totalCaloriasDia(Long idUsuario, LocalDate fecha);
}
