package com.perruno.Identificacion.Application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.perruno.Identificacion.Domain.Perro;
import com.perruno.Identificacion.Domain.UsuarioPerro;
import com.perruno.Identificacion.Domain.UsuarioPerro.UsuarioPerroKey;
import com.perruno.Identificacion.Infrastructure.PerroRepository;
import com.perruno.Identificacion.Infrastructure.UsuarioPerroRepository;

import java.time.LocalDateTime;

@Service
public class PerroService {

    @Autowired
    private PerroRepository perroRepository;
    
    @Autowired
    private UsuarioPerroRepository usuarioPerroRepository;
    
    /**
     * Registra un nuevo perro y lo asocia a un usuario como dueño principal
     */
    public Mono<Perro> registrarPerro(Perro perro, Integer idUsuario) {
        // Establecer fecha de registro y estado inicial
        perro.setFechaRegistro(LocalDateTime.now());
        perro.setEstado(Perro.Estado.activo);
        
        return perroRepository.save(perro)
            .flatMap(perroGuardado -> {
                // Crear la relación usuario-perro
                UsuarioPerro usuarioPerro = new UsuarioPerro();
                UsuarioPerroKey key = new UsuarioPerroKey();
                key.setIdUsuario(idUsuario);
                key.setIdPerro(perroGuardado.getIdPerro());
                usuarioPerro.setId(key);
                usuarioPerro.setEsDueñoPrincipal(true);
                
                return usuarioPerroRepository.save(usuarioPerro)
                    .thenReturn(perroGuardado);
            });
    }
    
    /**
     * Actualiza la información de un perro existente
     */
    public Mono<Perro> actualizarPerro(Integer idPerro, Perro perroActualizado) {
        return perroRepository.findById(idPerro)
            .flatMap(perroExistente -> {
                // Actualizar solo los campos proporcionados
                if (perroActualizado.getNombre() != null) {
                    perroExistente.setNombre(perroActualizado.getNombre());
                }
                if (perroActualizado.getRaza() != null) {
                    perroExistente.setRaza(perroActualizado.getRaza());
                }
                if (perroActualizado.getEdad() != null) {
                    perroExistente.setEdad(perroActualizado.getEdad());
                }
                if (perroActualizado.getColor() != null) {
                    perroExistente.setColor(perroActualizado.getColor());
                }
                if (perroActualizado.getTamaño() != null) {
                    perroExistente.setTamaño(perroActualizado.getTamaño());
                }
                if (perroActualizado.getFotoUrl() != null) {
                    perroExistente.setFotoUrl(perroActualizado.getFotoUrl());
                }
                if (perroActualizado.getChipCodigo() != null) {
                    perroExistente.setChipCodigo(perroActualizado.getChipCodigo());
                }
                if (perroActualizado.getQrCodigo() != null) {
                    perroExistente.setQrCodigo(perroActualizado.getQrCodigo());
                }
                if (perroActualizado.getEstado() != null) {
                    perroExistente.setEstado(perroActualizado.getEstado());
                }
                
                return perroRepository.save(perroExistente);
            });
    }
    
    /**
     * Obtiene un perro por su ID
     */
    public Mono<Perro> obtenerPerroPorId(Integer idPerro) {
        return perroRepository.findById(idPerro);
    }
    
    /**
     * Obtiene todos los perros registrados
     */
    public Flux<Perro> obtenerTodosLosPerros() {
        return perroRepository.findAll();
    }
    
    /**
     * Obtiene todos los perros de un usuario
     */
    public Flux<Perro> obtenerPerrosPorUsuario(Integer idUsuario) {
        return usuarioPerroRepository.findByIdIdUsuario(idUsuario)
            .flatMap(usuarioPerro -> perroRepository.findById(usuarioPerro.getId().getIdPerro()));
    }
}
