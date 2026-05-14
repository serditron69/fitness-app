package com.example.fitnessapp.service.impl;

import com.example.fitnessapp.model.Usuario;
import com.example.fitnessapp.repository.UsuarioRepository;
import com.example.fitnessapp.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;

    @Override public List<Usuario> listarTodos() { return usuarioRepository.findAll(); }
    @Override public Optional<Usuario> buscarPorId(Long id) { return usuarioRepository.findById(id); }
    @Override public Optional<Usuario> buscarPorEmail(String email) { return usuarioRepository.findByEmail(email); }
    @Override public Usuario guardar(Usuario u) { return usuarioRepository.save(u); }
    @Override public void eliminar(Long id) { usuarioRepository.deleteById(id); }

    @Override
    public Usuario actualizar(Long id, Usuario datos) {
        return usuarioRepository.findById(id).map(u -> {
            u.setNombre(datos.getNombre());
            u.setEmail(datos.getEmail());
            u.setPesoKg(datos.getPesoKg());
            u.setAlturaCm(datos.getAlturaCm());
            return usuarioRepository.save(u);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @Override
    public Optional<Usuario> login(String email, String password) {
        return usuarioRepository.findByEmail(email)
                .filter(u -> u.getPassword().equals(password));
    }
}
