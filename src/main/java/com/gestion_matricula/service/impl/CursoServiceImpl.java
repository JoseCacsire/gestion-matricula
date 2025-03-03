package com.gestion_matricula.service.impl;

import com.gestion_matricula.model.Curso;
import com.gestion_matricula.repo.CursoRepository;
import com.gestion_matricula.repo.IGenericRepo;
import com.gestion_matricula.service.CursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CursoServiceImpl extends CRUDImpl<Curso, String> implements CursoService {
    private final CursoRepository cursoRepository;

    @Override
    protected IGenericRepo<Curso, String> getRepo() {
        return cursoRepository;
    }

    public Mono<Void> delete(String id) {
        return cursoRepository.deleteById(id);
    }

}
