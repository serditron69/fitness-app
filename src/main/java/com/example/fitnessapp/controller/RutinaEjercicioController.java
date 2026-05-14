package com.example.fitnessapp.controller;

import com.example.fitnessapp.model.RutinaEjercicio;
import com.example.fitnessapp.service.RutinaEjercicioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rutina-ejercicios")
@RequiredArgsConstructor
@Tag(name = "Rutina-Ejercicios", description = "Ejercicios dentro de una rutina")
public class RutinaEjercicioController {
    private final RutinaEjercicioService rutinaEjercicioService;

    @GetMapping("/rutina/{idRutina}")
    public List<RutinaEjercicio> porRutina(@PathVariable Long idRutina) { return rutinaEjercicioService.listarPorRutina(idRutina); }

    @PostMapping
    public RutinaEjercicio crear(@RequestBody RutinaEjercicio re) { return rutinaEjercicioService.guardar(re); }

    @PutMapping("/{id}")
    public ResponseEntity<RutinaEjercicio> actualizar(@PathVariable Long id, @RequestBody RutinaEjercicio re) {
        return ResponseEntity.ok(rutinaEjercicioService.actualizar(id, re));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        rutinaEjercicioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
