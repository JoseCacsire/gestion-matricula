package com.gestion_matricula.service.impl;

import com.gestion_matricula.model.Estudiante;
import com.gestion_matricula.repo.EstudianteRepository;
import com.gestion_matricula.repo.IGenericRepo;
import com.gestion_matricula.service.EstudianteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class EstudianteServiceImpl extends CRUDImpl<Estudiante, String> implements EstudianteService {

    private final EstudianteRepository estudianteRepository;

    @Override
    protected IGenericRepo<Estudiante, String> getRepo() {
        return estudianteRepository;
    }

    @Override
    public Flux<Estudiante> findAllOrderByEdadAsc() {
        return estudianteRepository.findAllByOrderByEdadAsc();
    }

    @Override
    public Flux<Estudiante> findAllOrderByEdadDesc() {
        return estudianteRepository.findAllByOrderByEdadDesc();
    }


}
