package com.questionarioConex.avaliacao.controller;

import com.questionarioConex.avaliacao.dto.ResultadoAvaliacaoDTO;
import com.questionarioConex.avaliacao.dto.SubmissaoAvaliacaoDTO;
import com.questionarioConex.avaliacao.service.AvaliacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/avaliacao")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;

    @PostMapping("/submeter")
    public ResponseEntity<ResultadoAvaliacaoDTO> submeterAvaliacao(@Valid @RequestBody SubmissaoAvaliacaoDTO submissao) {
        ResultadoAvaliacaoDTO resultado = avaliacaoService.processarAvaliacao(submissao);
        return ResponseEntity.ok(resultado);
    }
}