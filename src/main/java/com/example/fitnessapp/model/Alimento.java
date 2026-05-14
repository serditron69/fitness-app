package com.example.fitnessapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "alimento")
@Data @NoArgsConstructor @AllArgsConstructor
public class Alimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alimento")
    private Long idAlimento;

    @Column(nullable = false, length = 150)
    private String nombre;

    // Valores: PROTEINA, CARBOHIDRATO, GRASA, FRUTA, VERDURA, LACTEO, BEBIDA, OTRO
    @Column(nullable = false, length = 50)
    private String categoria;

    // Valores por 100g
    @Column(name = "calorias_100g", nullable = false)
    private Double calorias100g;

    @Column(name = "proteinas_100g")
    private Double proteinas100g;

    @Column(name = "carbohidratos_100g")
    private Double carbohidratos100g;

    @Column(name = "grasas_100g")
    private Double grasas100g;

    @Column(name = "fibra_100g")
    private Double fibra100g;

    // Indica si fue importado de Open Food Facts o creado manualmente
    @Column(name = "fuente", length = 30)
    private String fuente = "MANUAL"; // MANUAL o OPEN_FOOD_FACTS

    @Column(nullable = false)
    private Boolean visible = true;

    @OneToMany(mappedBy = "alimento", cascade = CascadeType.ALL)
    @ToString.Exclude @EqualsAndHashCode.Exclude @JsonIgnore
    private List<DetalleComida> detalles;
}
