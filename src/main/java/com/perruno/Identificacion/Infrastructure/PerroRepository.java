package com.perruno.Identificacion.Infrastructure;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import com.perruno.Identificacion.Domain.Perro;

@Repository
public interface PerroRepository extends ReactiveCrudRepository<Perro, Integer>, PerroRepositoryCustom {
}
