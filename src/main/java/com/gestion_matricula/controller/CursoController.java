package com.gestion_matricula.controller;

import com.gestion_matricula.dto.CursoDTO;
import com.gestion_matricula.model.Curso;
import com.gestion_matricula.service.impl.CursoServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cursos")
public class CursoController {

    @Qualifier("defaultMapper")
    private final ModelMapper modelMapper;
    private final CursoServiceImpl cursoServiceImpl;

    @GetMapping
    public Mono<ResponseEntity<Flux<CursoDTO>>> getAllCursos() {

        Flux<CursoDTO> cursos = cursoServiceImpl.findAll()
                .map(this::convertToDto);

        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(cursos))
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<CursoDTO>> getCursoById(@PathVariable String id) {
        return cursoServiceImpl.findById(id)
                .map(this::convertToDto)
                .map(e-> ResponseEntity.ok()
                        .contentType((MediaType.APPLICATION_JSON))
                        .body(e)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<CursoDTO>> createCurso( @Valid @RequestBody CursoDTO cursoDTO) {
        return cursoServiceImpl.save(modelMapper.map(cursoDTO, Curso.class))
                .map(this::convertToDto)
                .map(e-> ResponseEntity.status(HttpStatus.CREATED)
                        .contentType((MediaType.APPLICATION_JSON))
                        .body(e)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<CursoDTO>> updateCurso(@PathVariable String id,@Valid  @RequestBody CursoDTO cursoDTO) {
        return Mono.just(cursoDTO)
                .map(e-> {
                    e.setId(id);
                    return e;
                })
                .flatMap(e-> cursoServiceImpl.update(id,convertToDocument(e)))
                .map(this::convertToDto)
                .map(e-> ResponseEntity.ok()
                        .contentType((MediaType.APPLICATION_JSON))
                        .body(e)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteCurso(@PathVariable String id) {
        return cursoServiceImpl.delete(id);
    }

    private CursoDTO convertToDto(Curso model){
        return modelMapper.map(model, CursoDTO.class);
    }

    private Curso convertToDocument(CursoDTO dto){
        return modelMapper.map(dto, Curso.class);
    }


}
