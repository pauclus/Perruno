package com.perruno.Identificacion.Infrastructure;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.perruno.Identificacion.Domain.VacunaPerro;

import java.time.LocalDate;

@Repository
public interface VacunaPerroRepository extends ReactiveCrudRepository<VacunaPerro, Integer> {
    
    // Encontrar todas las vacunas de un perro
    Flux<VacunaPerro> findByIdPerro(Integer idPerro);
    
    // Encontrar vacunas por perro y tipo de vacuna
    Flux<VacunaPerro> findByIdPerroAndIdVacuna(Integer idPerro, Integer idVacuna);
    
    // Encontrar vacunas que están por vencer (para notificaciones)
    Flux<VacunaPerro> findByFechaVencimientoBetween(LocalDate fechaInicio, LocalDate fechaFin);
    
    // Encontrar la última vacuna aplicada de un tipo específico a un perro
    Mono<VacunaPerro> findFirstByIdPerroAndIdVacunaOrderByFechaAplicacionDesc(Integer idPerro, Integer idVacuna);
}