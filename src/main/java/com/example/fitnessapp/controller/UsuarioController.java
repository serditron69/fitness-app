package com.example.fitnessapp.controller;

import com.example.fitnessapp.model.Usuario;
import com.example.fitnessapp.service.UsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Registro, login y gestion de usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> listar() { return usuarioService.listarTodos(); }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscar(@PathVariable Long id) {
        return usuarioService.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Usuario crear(@RequestBody Usuario usuario) { return usuarioService.guardar(usuario); }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.actualizar(id, usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestParam String email, @RequestParam String password) {
        return usuarioService.login(email, password)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }
}
