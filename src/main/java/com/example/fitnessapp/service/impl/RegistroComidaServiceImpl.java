package com.example.fitnessapp.service.impl;

import com.example.fitnessapp.dto.RegistroComidaCrearDto;
import com.example.fitnessapp.model.Alimento;
import com.example.fitnessapp.model.DetalleComida;
import com.example.fitnessapp.model.RegistroComida;
import com.example.fitnessapp.model.Usuario;
import com.example.fitnessapp.repository.AlimentoRepository;
import com.example.fitnessapp.repository.DetalleComidaRepository;
import com.example.fitnessapp.repository.RegistroComidaRepository;
import com.example.fitnessapp.repository.UsuarioRepository;
import com.example.fitnessapp.service.RegistroComidaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegistroComidaServiceImpl implements RegistroComidaService {

    private final RegistroComidaRepository registroComidaRepository;
    private final UsuarioRepository usuarioRepository;
    private final AlimentoRepository alimentoRepository;
    private final DetalleComidaRepository detalleComidaRepository;

    @Override
    public List<RegistroComida> listarPorUsuario(Long id) {
        return registroComidaRepository.findByUsuario_IdUsuario(id);
    }

    @Override
    public List<RegistroComida> listarPorUsuarioYFecha(Long id, LocalDate fecha) {
        return registroComidaRepository.findByUsuario_IdUsuarioAndFecha(id, fecha);
    }

    @Override
    public Optional<RegistroComida> buscarPorId(Long id) {
        return registroComidaRepository.findById(id);
    }

    @Override
    public RegistroComida guardar(RegistroComida r) {
        return registroComidaRepository.save(r);
    }

    @Override
    public RegistroComida guardarDesdeDto(RegistroComidaCrearDto dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuario().getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Alimento alimento = alimentoRepository.findById(dto.getAlimento().getIdAlimento())
                .orElseThrow(() -> new RuntimeException("Alimento no encontrado"));

        RegistroComida registro = new RegistroComida();
        registro.setUsuario(usuario);
        registro.setFecha(LocalDate.parse(dto.getFecha()));
        registro.setTipoComida(dto.getTipoComida());

        RegistroComida registroGuardado = registroComidaRepository.save(registro);

        DetalleComida detalle = new DetalleComida();
        detalle.setRegistroComida(registroGuardado);
        detalle.setAlimento(alimento);
        detalle.setCantidadGramos(dto.getCantidad());

        double caloriasConsumidas = (alimento.getCalorias100g() * dto.getCantidad()) / 100.0;
        detalle.setCaloriasConsumidas(caloriasConsumidas);

        detalleComidaRepository.save(detalle);

        return registroGuardado;
    }

    @Override
    public void eliminar(Long id) {
        registroComidaRepository.deleteById(id);
    }

    @Override
    public Double totalCaloriasDia(Long idUsuario, LocalDate fecha) {
        return registroComidaRepository.totalCaloriasDia(idUsuario, fecha);
    }

    @Override
    public RegistroComida actualizar(Long id, RegistroComida datos) {
        return registroComidaRepository.findById(id).map(r -> {
            r.setFecha(datos.getFecha());
            r.setTipoComida(datos.getTipoComida());
            r.setNotas(datos.getNotas());
            return registroComidaRepository.save(r);
        }).orElseThrow(() -> new RuntimeException("Registro de comida no encontrado"));
    }
}