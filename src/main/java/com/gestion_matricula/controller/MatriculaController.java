package com.gestion_matricula.controller;

import com.gestion_matricula.dto.MatriculaDTO;
import com.gestion_matricula.model.Matricula;
import com.gestion_matricula.service.impl.MatriculaServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/matriculas")
public class MatriculaController {

    @Qualifier("defaultMapper")
    private final ModelMapper modelMapper;
    private final MatriculaServiceImpl matriculaServiceImpl;

    @PostMapping
    public Mono<ResponseEntity<MatriculaDTO>> registrarMatricula(@Valid @RequestBody MatriculaDTO matriculaDTO) {
        return matriculaServiceImpl.save(modelMapper.map(matriculaDTO, Matricula.class))
                .map(this::convertToDto)
                .map(e -> ResponseEntity.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<MatriculaDTO>> getMatriculaById(@PathVariable String id) {
        return matriculaServiceImpl.findById(id)
                .map(this::convertToDto)
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    private MatriculaDTO convertToDto(Matricula model) {
        return modelMapper.map(model, MatriculaDTO.class);
    }

    private Matricula convertToDocument(MatriculaDTO dto) {
        return modelMapper.map(dto, Matricula.class);
    }
}
