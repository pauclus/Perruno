package com.perruno.Identificacion.Application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import com.perruno.Identificacion.Domain.Dueño;
import com.perruno.Identificacion.Infrastructure.DueñoRepository;
import com.perruno.Identificacion.Infrastructure.PerroRepository;

import java.time.LocalDateTime;

@Service
public class DueñoService {

    @Autowired
    private DueñoRepository dueñoRepository;
    
    @Autowired
    private PerroRepository perroRepository;
    
    /**
     * Registra un nuevo dueño
     */
    public Mono<Dueño> registrarDueño(Dueño dueño) {
        // Establecer fecha de registro
        dueño.setFechaRegistro(LocalDateTime.now());
        
        return dueñoRepository.save(dueño);
    }
    
    /**
     * Busca un dueño por su email
     */
    public Mono<Dueño> buscarPorEmail(String email) {
        return dueñoRepository.findByEmail(email);
    }
    
    /**
     * Obtiene un dueño por su ID
     */
    public Mono<Dueño> obtenerDueñoPorId(Integer idDueño) {
        return dueñoRepository.findById(idDueño);
    }
    
    /**
     * Actualiza la información de un dueño
     */
    public Mono<Dueño> actualizarDueño(Integer idDueño, Dueño dueñoActualizado) {
        return dueñoRepository.findById(idDueño)
            .flatMap(dueñoExistente -> {
                if (dueñoActualizado.getNombre() != null) {
                    dueñoExistente.setNombre(dueñoActualizado.getNombre());
                }
                if (dueñoActualizado.getEmail() != null) {
                    dueñoExistente.setEmail(dueñoActualizado.getEmail());
                }
                if (dueñoActualizado.getTelefono() != null) {
                    dueñoExistente.setTelefono(dueñoActualizado.getTelefono());
                }
                if (dueñoActualizado.getDireccion() != null) {
                    dueñoExistente.setDireccion(dueñoActualizado.getDireccion());
                }
                
                return dueñoRepository.save(dueñoExistente);
            });
    }
}