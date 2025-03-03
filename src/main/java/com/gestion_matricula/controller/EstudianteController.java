package com.gestion_matricula.controller;

import com.gestion_matricula.dto.EstudianteDTO;
import com.gestion_matricula.model.Estudiante;
import com.gestion_matricula.service.impl.EstudianteServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/estudiantes")
public class EstudianteController {

    @Qualifier("defaultMapper")
    private final ModelMapper modelMapper;
    private final EstudianteServiceImpl estudianteServiceImpl;

    @GetMapping
    public Mono<ResponseEntity<Flux<EstudianteDTO>>> getAllEstudiantes() {
        Flux<EstudianteDTO> estudiantes = estudianteServiceImpl.findAll()
                .map(this::convertToDto);

        return Mono.just(ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(estudiantes))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<EstudianteDTO>> getEstudianteById(@PathVariable String id) {
        return estudianteServiceImpl.findById(id)
                .map(this::convertToDto)
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<EstudianteDTO>> createEstudiante(@Valid @RequestBody EstudianteDTO estudianteDTO) {
        return estudianteServiceImpl.save(modelMapper.map(estudianteDTO, Estudiante.class))
                .map(this::convertToDto)
                .map(e -> ResponseEntity.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<EstudianteDTO>> updateEstudiante(@PathVariable String id, @RequestBody EstudianteDTO estudianteDTO) {
        return Mono.just(estudianteDTO)
                .map(e -> {
                    e.setId(id);
                    return e;
                })
                .flatMap(e -> estudianteServiceImpl.update(id, convertToDocument(e)))
                .map(this::convertToDto)
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteEstudiante(@PathVariable String id) {
        return estudianteServiceImpl.delete(id);
    }

    @GetMapping("/asc")
    public Flux<EstudianteDTO> getEstudiantesOrdenadosAsc() {
        return estudianteServiceImpl.findAllOrderByEdadAsc()
                .map(this::convertToDto);
    }

    @GetMapping("/desc")
    public Flux<EstudianteDTO> getEstudiantesOrdenadosDesc() {
        return estudianteServiceImpl.findAllOrderByEdadDesc()
                .map(this::convertToDto);
    }

    private EstudianteDTO convertToDto(Estudiante model) {
        return modelMapper.map(model, EstudianteDTO.class);
    }

    private Estudiante convertToDocument(EstudianteDTO dto) {
        return modelMapper.map(dto, Estudiante.class);
    }
}
