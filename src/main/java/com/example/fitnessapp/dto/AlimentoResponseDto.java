package com.example.fitnessapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlimentoResponseDto {
    private Long idAlimento;
    private String nombre;
    private Double calorias;
    private Double proteinas;
    private Double carbohidratos;
    private Double grasas;
}