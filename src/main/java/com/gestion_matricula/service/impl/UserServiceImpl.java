package com.gestion_matricula.service.impl;
import com.gestion_matricula.model.Role;
import com.gestion_matricula.model.User;
import com.gestion_matricula.repo.IGenericRepo;
import com.gestion_matricula.repo.IRoleRepo;
import com.gestion_matricula.repo.IUserRepo;
import com.gestion_matricula.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends CRUDImpl<User, String> implements IUserService {

    private final IUserRepo userRepo;
    private final IRoleRepo roleRepo;
    private final BCryptPasswordEncoder bcrypt;

    @Override
    protected IGenericRepo<User, String> getRepo() {
        return userRepo;
    }

    @Override
    public Mono<User> saveHash(User user) {
        user.setPassword(bcrypt.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public Mono<com.gestion_matricula.security.User> searchByUser(String username) {
        return userRepo.findOneByUsername(username)
                .flatMap(user -> Flux.fromIterable(user.getRoles())
                        .flatMap(userRole -> roleRepo.findById(userRole.getId())
                                .map(Role::getName))
                        .collectList()
                        .map(roles -> new com.gestion_matricula.security.User(user.getUsername(), user.getPassword(), user.isStatus(), roles))
                );
    }

    @Override
    public Mono<Void> createUserWithAdminRole() {
        String username = "admin";
        return userRepo.findOneByUsername(username)
                .switchIfEmpty(
                        roleRepo.findByName("ADMIN")  // Buscar por nombre en lugar de ID
                                .flatMap(role -> {
                                    User newUser = new User(
                                            username,
                                            "$2a$12$OC2Rt4tCq2l/9Tjs.KDP7eJHJnAjdh7/NqRCOoiBph3xDFxxIrp8i",
                                            true,
                                            List.of(role)
                                    );
                                    return userRepo.save(newUser);
                                })
                )
                .then();  // Si ya existe, simplemente se completa sin insertar
    }


    @Override
    public Mono<Void> createUserWithUserRole() {
        String username = "user";
        return userRepo.findOneByUsername(username)
                .switchIfEmpty(
                        roleRepo.findByName("USER")
                                .flatMap(role -> {
                                    User newUser = new User(
                                            username,
                                            "$2a$12$oxfL1WjUb75abWIy47.AUetGu8x.uklQYZwoQk04AkC2YsUERCMKa",
                                            true,
                                            List.of(role)
                                    );
                                    return userRepo.save(newUser);
                                })
                )
                .then();
    }

}
