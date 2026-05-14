package com.example.fitnessapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "rutina")
@Data @NoArgsConstructor @AllArgsConstructor
public class Rutina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rutina")
    private Long idRutina;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 300)
    private String descripcion;

    // Valores: FUERZA, CARDIO, MIXTO, FLEXIBILIDAD
    @Column(nullable = false, length = 30)
    private String objetivo;

    // Valores: PRINCIPIANTE, INTERMEDIO, AVANZADO
    @Column(nullable = false, length = 20)
    private String nivel;

    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;

    @Column(nullable = false)
    private Boolean activa = true;

    @OneToMany(mappedBy = "rutina", cascade = CascadeType.ALL)
    @ToString.Exclude @EqualsAndHashCode.Exclude @JsonIgnore
    private List<RutinaEjercicio> ejercicios;

    @OneToMany(mappedBy = "rutina", cascade = CascadeType.ALL)
    @ToString.Exclude @EqualsAndHashCode.Exclude @JsonIgnore
    private List<RegistroEntrenamiento> registros;
}
