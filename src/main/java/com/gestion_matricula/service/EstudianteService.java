package com.gestion_matricula.service;

import com.gestion_matricula.model.Estudiante;
import reactor.core.publisher.Flux;

public interface EstudianteService extends ICRUD<Estudiante, String> {

    Flux<Estudiante> findAllOrderByEdadAsc();

    Flux<Estudiante> findAllOrderByEdadDesc();
}
