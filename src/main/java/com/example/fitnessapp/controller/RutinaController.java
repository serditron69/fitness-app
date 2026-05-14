package com.example.fitnessapp.controller;

import com.example.fitnessapp.model.Rutina;
import com.example.fitnessapp.service.RutinaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rutinas")
@RequiredArgsConstructor
@Tag(name = "Rutinas", description = "Rutinas de entrenamiento por usuario")
public class RutinaController {
    private final RutinaService rutinaService;

    @GetMapping
    public List<Rutina> listar() { return rutinaService.listarTodas(); }

    @GetMapping("/{id}")
    public ResponseEntity<Rutina> buscar(@PathVariable Long id) {
        return rutinaService.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{idUsuario}")
    public List<Rutina> porUsuario(@PathVariable Long idUsuario) { return rutinaService.buscarPorUsuario(idUsuario); }

    @GetMapping("/usuario/{idUsuario}/activas")
    public List<Rutina> activas(@PathVariable Long idUsuario) { return rutinaService.buscarActivas(idUsuario); }

    @PostMapping
    public Rutina crear(@RequestBody Rutina rutina) { return rutinaService.guardar(rutina); }

    @PutMapping("/{id}")
    public ResponseEntity<Rutina> actualizar(@PathVariable Long id, @RequestBody Rutina rutina) {
        return ResponseEntity.ok(rutinaService.actualizar(id, rutina));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        rutinaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
