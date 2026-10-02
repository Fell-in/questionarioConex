package com.questionarioConex.avaliacao.controller;

import com.questionarioConex.avaliacao.dto.TopicoDTO;
import com.questionarioConex.avaliacao.service.TopicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/topicos")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class TopicoController {

    private final TopicoService topicoService;

    @GetMapping
    public ResponseEntity<List<TopicoDTO>> listarTodos() {
        return ResponseEntity.ok(topicoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(topicoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<TopicoDTO> criar(@Valid @RequestBody TopicoDTO dto) {
        TopicoDTO novoTopico = topicoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoTopico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicoDTO> atualizar(@PathVariable Long id, @Valid @RequestBody TopicoDTO dto) {
        return ResponseEntity.ok(topicoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        topicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}