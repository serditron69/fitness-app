package com.example.fitnessapp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "detalle_comida")
@Data @NoArgsConstructor @AllArgsConstructor
public class DetalleComida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Long idDetalle;

    @ManyToOne
    @JoinColumn(name = "id_registro_comida", nullable = false)
    private RegistroComida registroComida;

    @ManyToOne
    @JoinColumn(name = "id_alimento", nullable = false)
    private Alimento alimento;

    // Cantidad consumida en gramos
    @Column(nullable = false)
    private Double cantidadGramos;

    // Calculado: (calorias100g * cantidadGramos) / 100
    @Column(name = "calorias_consumidas")
    private Double caloriasConsumidas;
}
