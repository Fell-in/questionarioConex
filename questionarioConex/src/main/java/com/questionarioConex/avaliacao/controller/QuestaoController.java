package com.questionarioConex.avaliacao.controller;

import com.questionarioConex.avaliacao.domain.model.Questao;
import com.questionarioConex.avaliacao.domain.model.Topico;
import com.questionarioConex.avaliacao.domain.repository.QuestaoRepository;
import com.questionarioConex.avaliacao.domain.repository.TopicoRepository;
import com.questionarioConex.avaliacao.dto.QuestaoDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/questoes")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class QuestaoController {

    private final QuestaoRepository questaoRepository;
    private final TopicoRepository topicoRepository;

    @GetMapping
    public ResponseEntity<List<Questao>> listarTodas() {
        return ResponseEntity.ok(questaoRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<?> criarQuestao(@Valid @RequestBody QuestaoDTO dto) {
        Topico topico = topicoRepository.findById(dto.getTopicoId())
                .orElseThrow(() -> new RuntimeException("Tópico não encontrado"));

        Questao q = Questao.builder()
                .enunciado(dto.getEnunciado())
                .peso(dto.getPeso())
                .topico(topico)
                .opcoes(dto.getOpcoes())
                .respostaCorreta(dto.getRespostaCorreta())
                .build();

        return ResponseEntity.ok(questaoRepository.save(q));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarQuestao(@PathVariable Long id, @Valid @RequestBody QuestaoDTO dto) {
        Questao questao = questaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Questão não encontrada"));

        Topico topico = topicoRepository.findById(dto.getTopicoId())
                .orElseThrow(() -> new RuntimeException("Tópico não encontrado"));

        questao.setEnunciado(dto.getEnunciado());
        questao.setPeso(dto.getPeso());
        questao.setTopico(topico);
        questao.setOpcoes(dto.getOpcoes());
        questao.setRespostaCorreta(dto.getRespostaCorreta());

        return ResponseEntity.ok(questaoRepository.save(questao));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarQuestao(@PathVariable Long id) {
        questaoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}