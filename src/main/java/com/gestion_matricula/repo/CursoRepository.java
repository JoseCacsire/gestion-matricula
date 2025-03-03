package com.gestion_matricula.repo;


import com.gestion_matricula.model.Curso;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CursoRepository extends IGenericRepo<Curso, String> { }
