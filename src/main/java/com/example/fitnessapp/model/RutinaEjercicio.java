package com.example.fitnessapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rutina_ejercicio")
@Data @NoArgsConstructor @AllArgsConstructor
public class RutinaEjercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rutina_ejercicio")
    private Long idRutinaEjercicio;

    @ManyToOne
    @JoinColumn(name = "id_rutina", nullable = false)
    private Rutina rutina;

    @ManyToOne
    @JoinColumn(name = "id_ejercicio", nullable = false)
    private Ejercicio ejercicio;

    @Column(nullable = false)
    private Integer series;

    @Column(nullable = false)
    private Integer repeticiones;

    @Column(name = "peso_kg")
    private Double pesoKg;

    // Duracion en segundos (para ejercicios de cardio)
    @Column(name = "duracion_seg")
    private Integer duracionSeg;

    @Column(name = "tiempo_descanso_seg")
    private Integer tiempoDescansoSeg;

    private Integer orden;
}
