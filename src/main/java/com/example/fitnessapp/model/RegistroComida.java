package com.example.fitnessapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "registro_comida")
@Data @NoArgsConstructor @AllArgsConstructor
public class RegistroComida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_registro_comida")
    private Long idRegistroComida;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private LocalDate fecha;

    // Valores: DESAYUNO, ALMUERZO, CENA, SNACK
    @Column(nullable = false, length = 20)
    private String tipoComida;

    @Column(length = 300)
    private String notas;

    @OneToMany(mappedBy = "registroComida", cascade = CascadeType.ALL)
    @ToString.Exclude @EqualsAndHashCode.Exclude @JsonIgnore
    private List<DetalleComida> detalles;
}
