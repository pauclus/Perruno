package com.perruno.Identificacion.Infrastructure;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import com.perruno.Identificacion.Domain.Notificaciones;
import com.perruno.Identificacion.Domain.Notificaciones.Tipo;

@Repository
public interface NotificacionesRepository extends ReactiveCrudRepository<Notificaciones, Integer> {
    
    // Encontrar notificaciones por usuario
    Flux<Notificaciones> findByIdUsuario(Integer idUsuario);
    
    // Encontrar notificaciones no leídas por usuario
    Flux<Notificaciones> findByIdUsuarioAndLeida(Integer idUsuario, Boolean leida);
    
    // Encontrar notificaciones por tipo
    Flux<Notificaciones> findByIdUsuarioAndTipo(Integer idUsuario, Tipo tipo);
}