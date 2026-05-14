package com.example.fitnessapp.controller;

import com.example.fitnessapp.model.RegistroEntrenamiento;
import com.example.fitnessapp.service.RegistroEntrenamientoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registros")
@RequiredArgsConstructor
@Tag(name = "Registros", description = "Historial de entrenamientos realizados")
public class RegistroEntrenamientoController {
    private final RegistroEntrenamientoService registroService;

    @GetMapping
    public List<RegistroEntrenamiento> listar() { return registroService.listarTodos(); }

    @GetMapping("/usuario/{idUsuario}")
    public List<RegistroEntrenamiento> porUsuario(@PathVariable Long idUsuario) { return registroService.listarPorUsuario(idUsuario); }

    @GetMapping("/usuario/{idUsuario}/calorias-totales")
    public ResponseEntity<Integer> totalCalorias(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(registroService.totalCalorias(idUsuario));
    }

    @GetMapping("/usuario/{idUsuario}/mes/{mes}/anio/{anio}")
    public ResponseEntity<Long> entrenamientosMes(@PathVariable Long idUsuario, @PathVariable int mes, @PathVariable int anio) {
        return ResponseEntity.ok(registroService.entrenamientosMes(idUsuario, mes, anio));
    }

    @PostMapping
    public RegistroEntrenamiento crear(@RequestBody RegistroEntrenamiento r) { return registroService.guardar(r); }

    @PutMapping("/{id}")
    public ResponseEntity<RegistroEntrenamiento> actualizar(@PathVariable Long id, @RequestBody RegistroEntrenamiento r) {
        return ResponseEntity.ok(registroService.actualizar(id, r));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        registroService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
