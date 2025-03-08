package com.gestion_matricula.service;

import com.gestion_matricula.model.User;
import reactor.core.publisher.Mono;

public interface IUserService extends ICRUD<User, String>{

    Mono<User> saveHash(User user);
    Mono<com.gestion_matricula.security.User> searchByUser(String username);

    Mono<Void> createUserWithAdminRole();

    Mono<Void> createUserWithUserRole();
}
