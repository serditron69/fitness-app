package com.example.fitnessapp.service;

import com.example.fitnessapp.model.Usuario;

import java.util.List;
import java.util.Optional;
public interface UsuarioService {
    List<Usuario> listarTodos();
    Optional<Usuario> buscarPorId(Long id);
    Optional<Usuario> buscarPorEmail(String email);
    Usuario guardar(Usuario usuario);
    Usuario actualizar(Long id, Usuario usuario);
    void eliminar(Long id);
    Optional<Usuario> login(String email, String password);
}
