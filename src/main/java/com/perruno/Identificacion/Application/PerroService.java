package com.perruno.Identificacion.Application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import com.perruno.Identificacion.Domain.Perro;
import java.util.List;
import com.perruno.Identificacion.Domain.Dueño;
import com.perruno.Identificacion.Infrastructure.PerroRepository;
import com.perruno.Identificacion.Infrastructure.DueñoRepository;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class PerroService {

    @Autowired
    private PerroRepository perroRepository;
    
    @Autowired
    private DueñoRepository dueñoRepository;
    
    /**
     * Registra un nuevo perro asociándolo directamente a un dueño
     */
    public Mono<Perro> registrarPerro(Perro perro, Integer idDueño) {
        // Establecer fecha de registro, estado inicial y dueño
        perro.setFechaRegistro(LocalDateTime.now());
        perro.setEstado(Perro.Estado.activo);
        perro.setIdDueño(idDueño);
        
        return perroRepository.save(perro);
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
                if (perroActualizado.getIdDueño() != null) {
                    perroExistente.setIdDueño(perroActualizado.getIdDueño());
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
     * Obtiene todos los perros de un dueño
     */
    public Flux<Perro> obtenerPerrosPorDueño(Integer idDueño) {
        return perroRepository.findAll()
            .filter(perro -> perro.getIdDueño() != null && perro.getIdDueño().equals(idDueño));
    }
    
    /**
     * Cambia el dueño de un perro
     */
    public Mono<Perro> cambiarDueño(Integer idPerro, Integer nuevoDueñoId) {
        return perroRepository.findById(idPerro)
            .flatMap(perro -> {
                perro.setIdDueño(nuevoDueñoId);
                return perroRepository.save(perro);
            });
    }


    /**
     * Contar por raza
     */

     public Flux<Integer> contarPerrosPorRazas(List<String> razas) {
        return Flux.fromIterable(razas)
                   .flatMap(raza -> perroRepository.countByRaza(raza));
    }



}
