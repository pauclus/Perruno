package com.perruno.Identificacion.Infrastructure;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import com.perruno.Identificacion.Domain.Dueño;

@Repository
public interface DueñoRepository extends ReactiveCrudRepository<Dueño, Integer> {

    Mono<Dueño> findByEmail(String email);

}
