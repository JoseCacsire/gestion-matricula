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
        roleService.insertRolesIfAbsent()
                .then(userService.createUserWithAdminRole())  // Espera a que los roles se inserten antes de crear el usuario admin
                .then(userService.createUserWithUserRole())   // Luego crea el usuario "user"
                .subscribe();
    }


}