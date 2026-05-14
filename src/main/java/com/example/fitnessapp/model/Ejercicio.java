package com.example.fitnessapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "ejercicio")
@Data @NoArgsConstructor @AllArgsConstructor
public class Ejercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ejercicio")
    private Long idEjercicio;

    @Column(nullable = false, length = 100)
    private String nombre;

    // Valores: PECHO, ESPALDA, PIERNAS, HOMBROS, BICEPS, TRICEPS, ABDOMEN, CARDIO
    @Column(name = "grupo_muscular", nullable = false, length = 50)
    private String grupoMuscular;

    // Valores: FUERZA, CARDIO, FLEXIBILIDAD, EQUILIBRIO
    @Column(nullable = false, length = 30)
    private String tipo;

    @Column(length = 500)
    private String descripcion;

    @Column(length = 100)
    private String equipamiento;

    @Column(nullable = false)
    private Boolean visible = true;

    @OneToMany(mappedBy = "ejercicio", cascade = CascadeType.ALL)
    @ToString.Exclude @EqualsAndHashCode.Exclude @JsonIgnore
    private List<RutinaEjercicio> rutinaEjercicios;
}
