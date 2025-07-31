package com.perruno.Identificacion.Infrastructure;

import java.util.Map;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import com.perruno.Identificacion.Domain.Perro;
import com.perruno.Identificacion.Infrastructure.Custom.PerroRepositoryCustom;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Repository
public interface PerroRepository extends ReactiveCrudRepository<Perro, Integer>, PerroRepositoryCustom {
    @Query("SELECT raza, COUNT(*) AS cantidad FROM perros GROUP BY raza")
    Flux<Tuple2<String, Integer>> countPerrosPorRaza();

    Mono<Integer>countByRaza(String Raza);


    

}
