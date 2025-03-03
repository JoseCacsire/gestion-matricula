package com.gestion_matricula.config;

import com.gestion_matricula.handler.CursoHandler;
import com.gestion_matricula.handler.EstudianteHandler;
import com.gestion_matricula.handler.MatriculaHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    // Functional Endpoints
    @Bean
    public RouterFunction<ServerResponse> estudianteRoutes(EstudianteHandler estudianteHandler) {
        return route()
                .GET("/fun/estudiantes", estudianteHandler::getAllEstudiantes)
                .GET("/fun/estudiantes/{id}", estudianteHandler::getEstudianteById)
                .POST("/fun/estudiantes", estudianteHandler::createEstudiante)
                .PUT("/fun/estudiantes/{id}", estudianteHandler::updateEstudiante)
                .DELETE("/fun/estudiantes/{id}", estudianteHandler::deleteEstudiante)
                .GET("/fun/estudiantes/asc", estudianteHandler::getEstudiantesOrdenadosAsc)
                .GET("/fun/estudiantes/desc", estudianteHandler::getEstudiantesOrdenadosDesc)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> cursoRoutes(CursoHandler cursoHandler) {
        return route()
                .GET("/fun/cursos", cursoHandler::getAllCursos)
                .GET("/fun/cursos/{id}", cursoHandler::getCursoById)
                .POST("/fun/cursos", cursoHandler::createCurso)
                .PUT("/fun/cursos/{id}", cursoHandler::updateCurso)
                .DELETE("/fun/cursos/{id}", cursoHandler::deleteCurso)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> matriculaRoutes(MatriculaHandler matriculaHandler) {
        return route()
                .POST("/fun/matriculas", matriculaHandler::registrarMatricula)
                .GET("/fun/matriculas", matriculaHandler::getAllMatriculas)
                .build();
    }

}
