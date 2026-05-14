package com.example.fitnessapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "usuario")
@Data @NoArgsConstructor @AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    // Valores: ADMIN o USER
    @Column(nullable = false, length = 20)
    private String rol = "USER";

    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;

    @Column(name = "peso_kg")
    private Double pesoKg;

    @Column(name = "altura_cm")
    private Integer alturaCm;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @ToString.Exclude @EqualsAndHashCode.Exclude @JsonIgnore
    private List<Rutina> rutinas;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @ToString.Exclude @EqualsAndHashCode.Exclude @JsonIgnore
    private List<RegistroEntrenamiento> registros;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @ToString.Exclude @EqualsAndHashCode.Exclude @JsonIgnore
    private List<Progreso> progresos;
}
