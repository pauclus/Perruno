package com.perruno.Identificacion.Infrastructure;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.perruno.Identificacion.Domain.Vacuna;

@Repository
public interface VacunaRepository extends ReactiveCrudRepository<Vacuna, Integer> {
}