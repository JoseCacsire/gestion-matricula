package com.gestion_matricula.service.impl;
import com.gestion_matricula.model.Matricula;
import com.gestion_matricula.repo.IGenericRepo;
import com.gestion_matricula.repo.MatriculaRepository;
import com.gestion_matricula.service.MatriculaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatriculaServiceImpl extends CRUDImpl<Matricula, String> implements MatriculaService {

    private final MatriculaRepository matriculaRepository;

    @Override
    protected IGenericRepo<Matricula, String> getRepo() {
        return matriculaRepository;
    }

}