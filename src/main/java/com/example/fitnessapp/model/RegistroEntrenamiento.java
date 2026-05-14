package com.example.fitnessapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "registro_entrenamiento")
@Data @NoArgsConstructor @AllArgsConstructor
public class RegistroEntrenamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_registro")
    private Long idRegistro;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_rutina")
    private Rutina rutina;

    @Column(nullable = false)
    private LocalDate fecha;

    // Duracion en minutos
    @Column(name = "duracion_min")
    private Integer duracionMin;

    @Column(name = "calorias_quemadas")
    private Integer caloriasQuemadas;

    @Column(length = 300)
    private String notas;

    // Valores: COMPLETADO, PARCIAL, CANCELADO
    @Column(nullable = false, length = 20)
    private String estado = "COMPLETADO";
}
