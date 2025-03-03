package com.gestion_matricula.handler;

import com.gestion_matricula.dto.EstudianteDTO;
import com.gestion_matricula.model.Estudiante;
import com.gestion_matricula.service.impl.EstudianteServiceImpl;
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
public class EstudianteHandler {

    @Qualifier("defaultMapper")
    private final ModelMapper modelMapper;
    private final EstudianteServiceImpl estudianteServiceImpl;

    public Mono<ServerResponse> getAllEstudiantes(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(estudianteServiceImpl.findAll().map(this::convertToDto), EstudianteDTO.class);
    }

    public Mono<ServerResponse> getEstudianteById(ServerRequest request) {
        String id = request.pathVariable("id");
        return estudianteServiceImpl.findById(id)
                .map(this::convertToDto)
                .flatMap(estudiante -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(estudiante))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> createEstudiante(ServerRequest request) {
        Mono<EstudianteDTO> estudianteDTOMono = request.bodyToMono(EstudianteDTO.class);

        return estudianteDTOMono
                .flatMap(estudianteDTO -> estudianteServiceImpl.save(convertToDocument(estudianteDTO)))
                .map(this::convertToDto)
                .flatMap(estudiante -> ServerResponse.status(201)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(estudiante));
    }

    public Mono<ServerResponse> updateEstudiante(ServerRequest request) {
        String id = request.pathVariable("id");

        Mono<EstudianteDTO> estudianteDTOMono = request.bodyToMono(EstudianteDTO.class);

        return estudianteDTOMono
                .map(e -> {
                    e.setId(id);
                    return e;
                })
                .flatMap(estudianteDTO -> estudianteServiceImpl.save(convertToDocument(estudianteDTO)))
                .map(this::convertToDto)
                .flatMap(estudiante -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(estudiante));
    }

    public Mono<ServerResponse> deleteEstudiante(ServerRequest request) {
        String id = request.pathVariable("id");
        return estudianteServiceImpl.delete(id)
                .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> getEstudiantesOrdenadosAsc(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(estudianteServiceImpl.findAllOrderByEdadAsc().map(this::convertToDto), EstudianteDTO.class);
    }

    public Mono<ServerResponse> getEstudiantesOrdenadosDesc(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(estudianteServiceImpl.findAllOrderByEdadDesc().map(this::convertToDto), EstudianteDTO.class);
    }

    private EstudianteDTO convertToDto(Estudiante estudiante) {
        return modelMapper.map(estudiante, EstudianteDTO.class);
    }

    private Estudiante convertToDocument(EstudianteDTO estudianteDTO) {
        return modelMapper.map(estudianteDTO, Estudiante.class);
    }
}
