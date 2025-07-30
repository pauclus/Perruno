package com.perruno.Identificacion.Application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.perruno.Identificacion.Domain.Vacuna;
import com.perruno.Identificacion.Domain.VacunaPerro;
import com.perruno.Identificacion.Domain.Notificaciones;
import com.perruno.Identificacion.Domain.Notificaciones.Tipo;
import com.perruno.Identificacion.Domain.Perro;
import com.perruno.Identificacion.Infrastructure.VacunaRepository;
import com.perruno.Identificacion.Infrastructure.VacunaPerroRepository;
import com.perruno.Identificacion.Infrastructure.NotificacionesRepository;
import com.perruno.Identificacion.Infrastructure.PerroRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class VacunaService {

    @Autowired
    private VacunaRepository vacunaRepository;
    
    @Autowired
    private VacunaPerroRepository vacunaPerroRepository;
    
    @Autowired
    private NotificacionesRepository notificacionesRepository;
    
    @Autowired
    private PerroRepository perroRepository;
    
    /**
     * Crea una nueva vacuna en el sistema
     */
    public Mono<Vacuna> crearVacuna(Vacuna vacuna) {
        return vacunaRepository.save(vacuna);
    }
    
    /**
     * Registra la aplicación de una vacuna a un perro
     */
    public Mono<VacunaPerro> registrarVacunaPerro(VacunaPerro vacunaPerro) {
        // Si no se especifica fecha de vencimiento, calcularla (ejemplo: 1 año después de aplicación)
        if (vacunaPerro.getFechaVencimiento() == null && vacunaPerro.getFechaAplicacion() != null) {
            vacunaPerro.setFechaVencimiento(vacunaPerro.getFechaAplicacion().plusYears(1));
        }
        
        return vacunaPerroRepository.save(vacunaPerro)
            .flatMap(vacunaRegistrada -> {
                // Notificar al dueño sobre la vacuna aplicada
                return perroRepository.findById(vacunaPerro.getIdPerro())
                    .flatMap(perro -> {
                        if (perro.getIdDueño() != null) {
                            Notificaciones notificacion = new Notificaciones();
                            notificacion.setIdUsuario(perro.getIdDueño());
                            notificacion.setMensaje("Se ha registrado una nueva vacuna para tu mascota");
                            notificacion.setLeida(false);
                            notificacion.setTipo(Tipo.vacuna);
                            
                            return notificacionesRepository.save(notificacion)
                                .thenReturn(vacunaRegistrada);
                        }
                        return Mono.just(vacunaRegistrada);
                    })
                    .defaultIfEmpty(vacunaRegistrada); // En caso de no encontrar el perro
            });
    }
    
    /**
     * Obtiene todas las vacunas aplicadas a un perro
     */
    public Flux<VacunaPerro> obtenerVacunasPerro(Integer idPerro) {
        return vacunaPerroRepository.findByIdPerro(idPerro);
    }
    
    /**
     * Estima el tiempo restante de efectividad de una vacuna en días
     */
    public Mono<Long> estimarTiempoRestanteVacuna(Integer idVacunaPerro) {
        return vacunaPerroRepository.findById(idVacunaPerro)
            .map(vacunaPerro -> {
                LocalDate hoy = LocalDate.now();
                LocalDate fechaVencimiento = vacunaPerro.getFechaVencimiento();
                
                if (fechaVencimiento.isBefore(hoy)) {
                    return 0L; // La vacuna ya venció
                }
                
                return ChronoUnit.DAYS.between(hoy, fechaVencimiento);
            });
    }
    
    /**
     * Verifica las vacunas próximas a vencer y genera notificaciones
     * Este método podría ser ejecutado por un programador de tareas
     */
    public Flux<Notificaciones> verificarVacunasPorVencer() {
        LocalDate hoy = LocalDate.now();
        LocalDate enUnMes = hoy.plusMonths(1);
        
        return vacunaPerroRepository.findByFechaVencimientoBetween(hoy, enUnMes)
            .flatMap(vacunaPerro -> {
                return perroRepository.findById(vacunaPerro.getIdPerro())
                    .flatMap(perro -> {
                        if (perro.getIdDueño() != null) {
                            Notificaciones notificacion = new Notificaciones();
                            notificacion.setIdUsuario(perro.getIdDueño());
                            
                            long diasRestantes = ChronoUnit.DAYS.between(hoy, vacunaPerro.getFechaVencimiento());
                            notificacion.setMensaje("Una vacuna de tu mascota vencerá en " + diasRestantes + " días");
                            notificacion.setLeida(false);
                            notificacion.setTipo(Tipo.vacuna);
                            
                            return notificacionesRepository.save(notificacion);
                        }
                        return Mono.empty();
                    });
            });
    }
    
    /**
     * Obtiene todas las vacunas disponibles en el sistema
     */
    public Flux<Vacuna> obtenerTodasLasVacunas() {
        return vacunaRepository.findAll();
    }
}