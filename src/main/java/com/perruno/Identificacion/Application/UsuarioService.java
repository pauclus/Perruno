package com.perruno.Identificacion.Application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.perruno.Identificacion.Domain.Usuario;
import com.perruno.Identificacion.Domain.UsuarioPerro;
import com.perruno.Identificacion.Domain.UsuarioPerro.UsuarioPerroKey;
import com.perruno.Identificacion.Infrastructure.UsuarioRepository;
import com.perruno.Identificacion.Infrastructure.UsuarioPerroRepository;

import java.time.LocalDateTime;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private UsuarioPerroRepository usuarioPerroRepository;
    
    /**
     * Registra un nuevo usuario
     */
    public Mono<Usuario> registrarUsuario(Usuario usuario) {
        // Establecer fecha de registro
        usuario.setFechaRegistro(LocalDateTime.now());
        
        return usuarioRepository.save(usuario);
    }
    
    /**
     * Busca un usuario por su email
     */
    public Mono<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
    
    /**
     * Obtiene un usuario por su ID
     */
    public Mono<Usuario> obtenerUsuarioPorId(Integer idUsuario) {
        return usuarioRepository.findById(idUsuario);
    }
    
    /**
     * Asocia un perro a un usuario (no como dueño principal)
     */
    public Mono<UsuarioPerro> asociarPerroUsuario(Integer idUsuario, Integer idPerro) {
        UsuarioPerro usuarioPerro = new UsuarioPerro();
        UsuarioPerroKey key = new UsuarioPerroKey();
        key.setIdUsuario(idUsuario);
        key.setIdPerro(idPerro);
        usuarioPerro.setId(key);
        usuarioPerro.setEsDueñoPrincipal(false); // No es dueño principal por defecto
        
        return usuarioPerroRepository.save(usuarioPerro);
    }
    
    /**
     * Cambia el dueño principal de un perro
     */
    public Mono<Void> cambiarDueñoPrincipal(Integer idPerro, Integer nuevoIdUsuario) {
        // Primero, quitar el estado de dueño principal al dueño actual
        return usuarioPerroRepository.findDueñoPrincipal(idPerro)
            .flatMap(dueñoActual -> {
                dueñoActual.setEsDueñoPrincipal(false);
                return usuarioPerroRepository.save(dueñoActual);
            })
            .then(Mono.defer(() -> {
                // Luego, establecer el nuevo dueño principal
                return usuarioPerroRepository.findByIdIdPerroAndIdIdUsuario(idPerro, nuevoIdUsuario)
                    .flatMap(nuevoDueño -> {
                        nuevoDueño.setEsDueñoPrincipal(true);
                        return usuarioPerroRepository.save(nuevoDueño);
                    })
                    .switchIfEmpty(Mono.defer(() -> {
                        // Si no existe la relación, crearla
                        UsuarioPerro usuarioPerro = new UsuarioPerro();
                        UsuarioPerroKey key = new UsuarioPerroKey();
                        key.setIdUsuario(nuevoIdUsuario);
                        key.setIdPerro(idPerro);
                        usuarioPerro.setId(key);
                        usuarioPerro.setEsDueñoPrincipal(true);
                        
                        return usuarioPerroRepository.save(usuarioPerro);
                    }));
            }))
            .then();
    }
    
    /**
     * Elimina la asociación entre un usuario y un perro
     */
    public Mono<Void> desasociarPerroUsuario(Integer idUsuario, Integer idPerro) {
        return usuarioPerroRepository.findByIdIdPerroAndIdIdUsuario(idPerro, idUsuario)
            .flatMap(usuarioPerro -> {
                // No permitir eliminar al dueño principal
                if (usuarioPerro.getEsDueñoPrincipal()) {
                    return Mono.error(new IllegalStateException("No se puede eliminar al dueño principal"));
                }
                return usuarioPerroRepository.delete(usuarioPerro);
            })
            .then();
    }
}