package com.example.fitnessapp.service;
import com.example.fitnessapp.model.DetalleComida;
import java.util.List;
public interface DetalleComidaService {
    List<DetalleComida> listarPorRegistro(Long idRegistro);
    DetalleComida guardar(DetalleComida d);
    void eliminar(Long id);
}
