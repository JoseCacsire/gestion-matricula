package com.gestion_matricula.initializer;
import com.gestion_matricula.service.IUserService;
import com.gestion_matricula.service.impl.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleService roleService;
    private final IUserService userService;

    @Override
    public void run(String... args) throws Exception {
        // Insertar los roles ADMIN y USER al iniciar la aplicación
        roleService.insertRolesIfAbsent().subscribe();
        // Insertar el usuario admin al iniciar la aplicación
        userService.createUserWithAdminRole().subscribe();
        // Insertar el usuario user al iniciar la aplicación
        userService.createUserWithUserRole().subscribe();

    }

}