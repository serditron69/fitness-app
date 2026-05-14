package com.example.fitnessapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "progreso")
@Data @NoArgsConstructor @AllArgsConstructor
public class Progreso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_progreso")
    private Long idProgreso;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(name = "peso_kg")
    private Double pesoKg;

    @Column(name = "grasa_corporal_pct")
    private Double grasaCorporalPct;

    @Column(name = "masa_muscular_kg")
    private Double masaMuscularKg;

    @Column(name = "imc")
    private Double imc;

    @Column(length = 300)
    private String notas;
}
