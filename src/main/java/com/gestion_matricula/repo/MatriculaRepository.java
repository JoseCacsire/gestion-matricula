package com.gestion_matricula.repo;


import com.gestion_matricula.model.Matricula;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MatriculaRepository extends IGenericRepo<Matricula, String> { }

