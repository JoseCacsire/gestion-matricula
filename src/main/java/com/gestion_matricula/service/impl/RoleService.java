package com.gestion_matricula.service.impl;

import com.gestion_matricula.model.Role;
import com.gestion_matricula.repo.IRoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final IRoleRepo roleRepo;

    // Método para insertar roles si no existen
    public Mono<Void> insertRolesIfAbsent() {
        // Insertar el rol ADMIN si no existe
        Mono<Role> adminRole = roleRepo.findByName("ADMIN").switchIfEmpty(roleRepo.save(new Role("ADMIN")));

        // Insertar el rol USER si no existe
        Mono<Role> userRole = roleRepo.findByName("USER").switchIfEmpty(roleRepo.save(new Role("USER")));

        return Mono.zip(adminRole, userRole).then();
    }
}
