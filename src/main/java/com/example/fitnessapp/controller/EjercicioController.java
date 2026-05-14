package com.example.fitnessapp.controller;

import com.example.fitnessapp.model.Ejercicio;
import com.example.fitnessapp.service.EjercicioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ejercicios")
@RequiredArgsConstructor
@Tag(name = "Ejercicios", description = "Catalogo de ejercicios (admin)")
public class EjercicioController {
    private final EjercicioService ejercicioService;

    @GetMapping
    public List<Ejercicio> listar() { return ejercicioService.listarTodos(); }

    @GetMapping("/{id}")
    public ResponseEntity<Ejercicio> buscar(@PathVariable Long id) {
        return ejercicioService.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/grupo/{grupo}")
    public List<Ejercicio> porGrupo(@PathVariable String grupo) { return ejercicioService.buscarPorGrupoMuscular(grupo); }

    @GetMapping("/tipo/{tipo}")
    public List<Ejercicio> porTipo(@PathVariable String tipo) { return ejercicioService.buscarPorTipo(tipo); }

    @PostMapping
    public Ejercicio crear(@RequestBody Ejercicio ejercicio) { return ejercicioService.guardar(ejercicio); }

    @PutMapping("/{id}")
    public ResponseEntity<Ejercicio> actualizar(@PathVariable Long id, @RequestBody Ejercicio ejercicio) {
        return ResponseEntity.ok(ejercicioService.actualizar(id, ejercicio));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        ejercicioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/visibilidad")
    public ResponseEntity<Ejercicio> visibilidad(@PathVariable Long id, @RequestParam Boolean visible) {
        return ResponseEntity.ok(ejercicioService.cambiarVisibilidad(id, visible));
    }
}
