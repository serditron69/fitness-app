package com.example.fitnessapp.dto;

import lombok.Data;

@Data
public class RegistroComidaCrearDto {
    private UsuarioRefDto usuario;
    private AlimentoIdDto alimento;
    private Double cantidad;
    private String fecha;
    private String tipoComida;
}