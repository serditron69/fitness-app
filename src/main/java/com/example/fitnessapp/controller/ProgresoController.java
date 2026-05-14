package com.example.fitnessapp.controller;

import com.example.fitnessapp.model.Progreso;
import com.example.fitnessapp.service.ProgresoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progresos")
@RequiredArgsConstructor
@Tag(name = "Progresos", description = "Seguimiento de peso, IMC y composicion corporal")
public class ProgresoController {
    private final ProgresoService progresoService;

    @GetMapping("/usuario/{idUsuario}")
    public List<Progreso> porUsuario(@PathVariable Long idUsuario) { return progresoService.listarPorUsuario(idUsuario); }

    @PostMapping
    public Progreso crear(@RequestBody Progreso p) { return progresoService.guardar(p); }

    @PutMapping("/{id}")
    public ResponseEntity<Progreso> actualizar(@PathVariable Long id, @RequestBody Progreso p) {
        return ResponseEntity.ok(progresoService.actualizar(id, p));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        progresoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
