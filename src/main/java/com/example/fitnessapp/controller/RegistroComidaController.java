package com.example.fitnessapp.controller;

import com.example.fitnessapp.dto.RegistroComidaCrearDto;
import com.example.fitnessapp.dto.RegistroComidaResponseDto;
import com.example.fitnessapp.model.RegistroComida;
import com.example.fitnessapp.service.RegistroComidaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/registro-comidas")
@RequiredArgsConstructor
@Tag(name = "Registro Comidas", description = "Registro diario de comidas y calorias consumidas")
public class RegistroComidaController {

    private final RegistroComidaService registroComidaService;

    @GetMapping("/usuario/{idUsuario}")
    public List<RegistroComida> porUsuario(@PathVariable Long idUsuario) {
        return registroComidaService.listarPorUsuario(idUsuario);
    }

    @GetMapping("/usuario/{idUsuario}/fecha/{fecha}")
    public List<RegistroComidaResponseDto> porUsuarioYFecha(
            @PathVariable Long idUsuario,
            @PathVariable LocalDate fecha
    ) {
        return registroComidaService.listarPorUsuarioYFecha(idUsuario, fecha);
    }

    @GetMapping("/usuario/{idUsuario}/calorias-dia/{fecha}")
    public ResponseEntity<Double> totalCaloriasDia(
            @PathVariable Long idUsuario,
            @PathVariable LocalDate fecha
    ) {
        return ResponseEntity.ok(registroComidaService.totalCaloriasDia(idUsuario, fecha));
    }

    @PostMapping
    public RegistroComida crear(@RequestBody RegistroComidaCrearDto dto) {
        return registroComidaService.guardarDesdeDto(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegistroComida> actualizar(
            @PathVariable Long id,
            @RequestBody RegistroComida r
    ) {
        return ResponseEntity.ok(registroComidaService.actualizar(id, r));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        registroComidaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}