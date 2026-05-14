package com.example.fitnessapp.controller;

import com.example.fitnessapp.dto.AlimentoDTO;
import com.example.fitnessapp.model.Alimento;
import com.example.fitnessapp.service.AlimentoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/alimentos")
@RequiredArgsConstructor
@Tag(name = "Alimentos", description = "Catalogo de alimentos con calorias y macronutrientes")
public class AlimentoController {
    private final AlimentoService alimentoService;

    @GetMapping
    public List<Alimento> listar() { return alimentoService.listarTodos(); }

    @GetMapping("/{id}")
    public ResponseEntity<Alimento> buscar(@PathVariable Long id) {
        return alimentoService.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    public List<Alimento> buscarPorNombre(@RequestParam String nombre) {
        return alimentoService.buscarPorNombre(nombre);
    }

    @GetMapping("/categoria/{categoria}")
    public List<Alimento> porCategoria(@PathVariable String categoria) {
        return alimentoService.buscarPorCategoria(categoria);
    }

    @PostMapping
    public Alimento crear(@RequestBody Alimento alimento) { return alimentoService.guardar(alimento); }

    @PutMapping("/{id}")
    public ResponseEntity<Alimento> actualizar(@PathVariable Long id, @RequestBody Alimento alimento) {
        return ResponseEntity.ok(alimentoService.actualizar(id, alimento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        alimentoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // Busca en Open Food Facts sin guardar
    @GetMapping("/open-food-facts/buscar")
    public List<AlimentoDTO> buscarEnOpenFoodFacts(@RequestParam String nombre) {
        return alimentoService.buscarEnOpenFoodFacts(nombre);
    }

    // Importa desde Open Food Facts y guarda en BD
    @PostMapping("/open-food-facts/importar")
    public Alimento importarDeOpenFoodFacts(@RequestBody AlimentoDTO dto) {
        return alimentoService.importarDeOpenFoodFacts(dto);
    }
}
