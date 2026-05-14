package com.example.fitnessapp.dto;

import lombok.Data;

@Data
public class AlimentoDTO {
    private String nombre;
    private Double calorias100g;
    private Double proteinas100g;
    private Double carbohidratos100g;
    private Double grasas100g;
    private Double fibra100g;
    private String fuente;
}
