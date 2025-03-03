package com.gestion_matricula.handler;

import com.gestion_matricula.dto.MatriculaDTO;
import com.gestion_matricula.model.Matricula;
import com.gestion_matricula.service.impl.MatriculaServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MatriculaHandler {

    @Qualifier("defaultMapper")
    private final ModelMapper modelMapper;
    private final MatriculaServiceImpl matriculaServiceImpl;

    public Mono<ServerResponse> registrarMatricula(ServerRequest request) {

        Mono<MatriculaDTO>  matriculaDTOMono = request.bodyToMono(MatriculaDTO.class);

        return matriculaDTOMono
                .flatMap(matriculaDTO -> matriculaServiceImpl.save(convertToDocument(matriculaDTO)))
                .map(this::convertToDto)
                .flatMap(matricula -> ServerResponse.status(201)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(matricula));

    }

    public Mono<ServerResponse> getAllMatriculas(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(matriculaServiceImpl.findAll().map(this::convertToDto), MatriculaDTO.class);
    }

    private MatriculaDTO convertToDto(Matricula matricula) {
        return modelMapper.map(matricula, MatriculaDTO.class);
    }

    private Matricula convertToDocument(MatriculaDTO matriculaDTO) {
        return modelMapper.map(matriculaDTO, Matricula.class);
    }

}
