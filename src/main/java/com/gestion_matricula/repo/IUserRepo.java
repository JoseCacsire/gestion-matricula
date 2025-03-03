package com.gestion_matricula.repo;

import com.gestion_matricula.model.User;
import reactor.core.publisher.Mono;

public interface IUserRepo extends IGenericRepo<User, String> {

    //@Query("{ username:  ?1}")
    Mono<User> findOneByUsername(String username);

}


