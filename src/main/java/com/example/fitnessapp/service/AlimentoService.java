package com.example.fitnessapp.service;
import com.example.fitnessapp.dto.AlimentoDTO;
import com.example.fitnessapp.model.Alimento;
import java.util.List;
import java.util.Optional;
public interface AlimentoService {
    List<Alimento> listarTodos();
    Optional<Alimento> buscarPorId(Long id);
    List<Alimento> buscarPorNombre(String nombre);
    List<Alimento> buscarPorCategoria(String categoria);
    Alimento guardar(Alimento alimento);
    Alimento actualizar(Long id, Alimento alimento);
    void eliminar(Long id);
    // Busca en Open Food Facts y devuelve lista de DTOs sin guardar
    List<AlimentoDTO> buscarEnOpenFoodFacts(String nombre);
    // Importa desde Open Food Facts y lo guarda en BD
    Alimento importarDeOpenFoodFacts(AlimentoDTO dto);
}
