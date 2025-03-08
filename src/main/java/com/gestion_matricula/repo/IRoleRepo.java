package com.gestion_matricula.repo;


import com.gestion_matricula.model.Role;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IRoleRepo extends IGenericRepo<Role, String> {
    Mono<Role> findByName(String admin);
}
