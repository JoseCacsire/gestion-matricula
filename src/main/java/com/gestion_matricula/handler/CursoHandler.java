package com.gestion_matricula.handler;

import com.gestion_matricula.dto.CursoDTO;
import com.gestion_matricula.model.Curso;
import com.gestion_matricula.service.impl.CursoServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CursoHandler {

    @Qualifier("defaultMapper")
    private final ModelMapper modelMapper;
    private final CursoServiceImpl cursoServiceImpl;


    public Mono<ServerResponse> getAllCursos(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(cursoServiceImpl.findAll().map(this::convertToDto), CursoDTO.class);
    }

    public Mono<ServerResponse> getCursoById(ServerRequest request) {
        String id = request.pathVariable("id");
        return cursoServiceImpl.findById(id)
                .map(this::convertToDto)
                .flatMap(curso -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(curso))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> createCurso(ServerRequest request) {

        Mono<CursoDTO> cursoDTOMono = request.bodyToMono(CursoDTO.class);

        return cursoDTOMono
                .flatMap(e -> cursoServiceImpl.save(convertToDocument(e)))
                .map(this::convertToDto)
                .flatMap(e -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(e));

    }

    public Mono<ServerResponse> updateCurso(ServerRequest request) {
        String id = request.pathVariable("id");

        Mono<CursoDTO> cursoDTOMono = request.bodyToMono(CursoDTO.class);

        return cursoDTOMono
                .map(e->{
                    e.setId(id);
                    return e;
                })
                .flatMap(e -> cursoServiceImpl.update(id, convertToDocument(e)))
                .map(this::convertToDto)
                .flatMap(e -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(e));
    }

    public Mono<ServerResponse> deleteCurso(ServerRequest request) {
        String id = request.pathVariable("id");
        return cursoServiceImpl.delete(id)
                .then(ServerResponse.noContent().build());
    }

    private CursoDTO convertToDto(Curso model){
        return modelMapper.map(model, CursoDTO.class);
    }

    private Curso convertToDocument(CursoDTO dto){
        return modelMapper.map(dto, Curso.class);
    }

}