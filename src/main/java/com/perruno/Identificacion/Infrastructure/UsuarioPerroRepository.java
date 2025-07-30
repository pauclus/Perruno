package com.perruno.Identificacion.Infrastructure;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.perruno.Identificacion.Domain.UsuarioPerro;
import com.perruno.Identificacion.Domain.UsuarioPerro.UsuarioPerroKey;

@Repository
public interface UsuarioPerroRepository extends ReactiveCrudRepository<UsuarioPerro, UsuarioPerroKey> {
    
    // Encontrar todos los perros asociados a un usuario
    Flux<UsuarioPerro> findByIdIdUsuario(Integer idUsuario);
    
    // Encontrar todos los usuarios asociados a un perro
    Flux<UsuarioPerro> findByIdIdPerro(Integer idPerro);
    
    // Encontrar el dueño principal de un perro
    Mono<UsuarioPerro> findByIdIdPerroAndEsDueñoPrincipal(Integer idPerro, Boolean esDueñoPrincipal);



}