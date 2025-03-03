package com.gestion_matricula.repo;

import com.gestion_matricula.model.Estudiante;
import reactor.core.publisher.Flux;

public interface EstudianteRepository extends IGenericRepo<Estudiante, String> {
    Flux<Estudiante> findAllByOrderByEdadAsc();
    Flux<Estudiante> findAllByOrderByEdadDesc();
}

