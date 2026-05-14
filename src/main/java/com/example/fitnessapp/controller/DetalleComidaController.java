package com.example.fitnessapp.controller;

import com.example.fitnessapp.model.DetalleComida;
import com.example.fitnessapp.service.DetalleComidaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/detalle-comidas")
@RequiredArgsConstructor
@Tag(name = "Detalle Comidas", description = "Alimentos y cantidades dentro de cada registro de comida")
public class DetalleComidaController {
    private final DetalleComidaService detalleComidaService;

    @GetMapping("/registro/{idRegistro}")
    public List<DetalleComida> porRegistro(@PathVariable Long idRegistro) {
        return detalleComidaService.listarPorRegistro(idRegistro);
    }

    @PostMapping
    public DetalleComida crear(@RequestBody DetalleComida d) { return detalleComidaService.guardar(d); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        detalleComidaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
