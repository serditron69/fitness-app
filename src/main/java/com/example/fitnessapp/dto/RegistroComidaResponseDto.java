package com.example.fitnessapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistroComidaResponseDto {
    private Long idRegistroComida;
    private String fecha;
    private String tipoComida;
    private Double cantidad;
    private AlimentoResponseDto alimento;
}