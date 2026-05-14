package com.example.fitnessapp.service.impl;

import com.example.fitnessapp.model.DetalleComida;
import com.example.fitnessapp.repository.DetalleComidaRepository;
import com.example.fitnessapp.service.DetalleComidaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DetalleComidaServiceImpl implements DetalleComidaService {
    private final DetalleComidaRepository detalleComidaRepository;

    @Override
    public List<DetalleComida> listarPorRegistro(Long idRegistro) {
        return detalleComidaRepository.findByRegistroComida_IdRegistroComida(idRegistro);
    }

    @Override
    public DetalleComida guardar(DetalleComida d) {
        // Calcula calorías automáticamente
        if (d.getAlimento() != null && d.getCantidadGramos() != null) {
            double calorias = (d.getAlimento().getCalorias100g() * d.getCantidadGramos()) / 100;
            d.setCaloriasConsumidas(calorias);
        }
        return detalleComidaRepository.save(d);
    }

    @Override
    public void eliminar(Long id) { detalleComidaRepository.deleteById(id); }
}
