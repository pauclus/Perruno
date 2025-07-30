package com.perruno.Identificacion.Application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.perruno.Identificacion.Domain.Notificaciones;
import com.perruno.Identificacion.Domain.Notificaciones.Tipo;
import com.perruno.Identificacion.Infrastructure.NotificacionesRepository;

@Service
public class NotificacionesService {

    @Autowired
    private NotificacionesRepository notificacionesRepository;
    
    /**
     * Crea una nueva notificación
     */
    public Mono<Notificaciones> crearNotificacion(Notificaciones notificacion) {
        return notificacionesRepository.save(notificacion);
    }
    
    /**
     * Obtiene todas las notificaciones de un usuario
     */
    public Flux<Notificaciones> obtenerNotificacionesUsuario(Integer idUsuario) {
        return notificacionesRepository.findByIdUsuario(idUsuario);
    }
    
    /**
     * Obtiene las notificaciones no leídas de un usuario
     */
    public Flux<Notificaciones> obtenerNotificacionesNoLeidas(Integer idUsuario) {
        return notificacionesRepository.findByIdUsuarioAndLeida(idUsuario, false);
    }
    
    /**
     * Marca una notificación como leída
     */
    public Mono<Notificaciones> marcarComoLeida(Integer idNotificacion) {
        return notificacionesRepository.findById(idNotificacion)
            .flatMap(notificacion -> {
                notificacion.setLeida(true);
                return notificacionesRepository.save(notificacion);
            });
    }
    
    /**
     * Obtiene notificaciones por tipo
     */
    public Flux<Notificaciones> obtenerNotificacionesPorTipo(Integer idUsuario, Tipo tipo) {
        return notificacionesRepository.findByIdUsuarioAndTipo(idUsuario, tipo);
    }
}