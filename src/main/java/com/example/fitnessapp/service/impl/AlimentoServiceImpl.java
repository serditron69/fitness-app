package com.example.fitnessapp.service.impl;

import com.example.fitnessapp.dto.AlimentoDTO;
import com.example.fitnessapp.model.Alimento;
import com.example.fitnessapp.repository.AlimentoRepository;
import com.example.fitnessapp.service.AlimentoService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlimentoServiceImpl implements AlimentoService {

    private final AlimentoRepository alimentoRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Override public List<Alimento> listarTodos() { return alimentoRepository.findAll(); }
    @Override public Optional<Alimento> buscarPorId(Long id) { return alimentoRepository.findById(id); }
    @Override public List<Alimento> buscarPorNombre(String nombre) { return alimentoRepository.findByNombreContainingIgnoreCase(nombre); }
    @Override public List<Alimento> buscarPorCategoria(String categoria) { return alimentoRepository.findByCategoria(categoria); }
    @Override public Alimento guardar(Alimento a) { return alimentoRepository.save(a); }
    @Override public void eliminar(Long id) { alimentoRepository.deleteById(id); }

    @Override
    public Alimento actualizar(Long id, Alimento datos) {
        return alimentoRepository.findById(id).map(a -> {
            a.setNombre(datos.getNombre());
            a.setCategoria(datos.getCategoria());
            a.setCalorias100g(datos.getCalorias100g());
            a.setProteinas100g(datos.getProteinas100g());
            a.setCarbohidratos100g(datos.getCarbohidratos100g());
            a.setGrasas100g(datos.getGrasas100g());
            a.setFibra100g(datos.getFibra100g());
            return alimentoRepository.save(a);
        }).orElseThrow(() -> new RuntimeException("Alimento no encontrado"));
    }

    @Override
    public List<AlimentoDTO> buscarEnOpenFoodFacts(String nombre) {
        List<AlimentoDTO> resultados = new ArrayList<>();
        try {
            String url = "https://world.openfoodfacts.org/cgi/search.pl?search_terms="
                    + nombre.replace(" ", "+")
                    + "&json=true&page_size=50&fields=product_name,nutriments";

            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);
            JsonNode products = root.path("products");

            for (JsonNode product : products) {
                String productName = product.path("product_name").asText("");
                if (productName.isBlank()) continue;

                JsonNode nutriments = product.path("nutriments");
                AlimentoDTO dto = new AlimentoDTO();
                dto.setNombre(productName);
                dto.setCalorias100g(nutriments.path("energy-kcal_100g").asDouble(0));
                dto.setProteinas100g(nutriments.path("proteins_100g").asDouble(0));
                dto.setCarbohidratos100g(nutriments.path("carbohydrates_100g").asDouble(0));
                dto.setGrasas100g(nutriments.path("fat_100g").asDouble(0));
                dto.setFibra100g(nutriments.path("fiber_100g").asDouble(0));
                dto.setFuente("OPEN_FOOD_FACTS");
                resultados.add(dto);
            }
        } catch (Exception e) {
            // Si falla la API retorna lista vacía
        }
        return resultados;
    }

    @Override
    public Alimento importarDeOpenFoodFacts(AlimentoDTO dto) {
        Alimento alimento = new Alimento();
        alimento.setNombre(dto.getNombre());
        alimento.setCategoria("OTRO");
        alimento.setCalorias100g(dto.getCalorias100g());
        alimento.setProteinas100g(dto.getProteinas100g());
        alimento.setCarbohidratos100g(dto.getCarbohidratos100g());
        alimento.setGrasas100g(dto.getGrasas100g());
        alimento.setFibra100g(dto.getFibra100g());
        alimento.setFuente("OPEN_FOOD_FACTS");
        alimento.setVisible(true);
        return alimentoRepository.save(alimento);
    }
}
