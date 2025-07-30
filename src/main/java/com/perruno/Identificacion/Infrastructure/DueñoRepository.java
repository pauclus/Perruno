package com.perruno.Identificacion.Infrastructure;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import com.perruno.Identificacion.Domain.Usuario;

@Repository
public interface UsuarioRepository extends ReactiveCrudRepository<Usuario, Integer> {

    Mono<Usuario> findByEmail(String email);

}
