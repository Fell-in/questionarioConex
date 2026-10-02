package com.questionarioConex.avaliacao.service;

import com.questionarioConex.avaliacao.domain.model.Questao;
import com.questionarioConex.avaliacao.domain.model.Topico;
import com.questionarioConex.avaliacao.domain.repository.QuestaoRepository;
import com.questionarioConex.avaliacao.domain.repository.TopicoRepository;
import com.questionarioConex.avaliacao.dto.QuestaoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestaoService {

    private final QuestaoRepository questaoRepository;
    private final TopicoRepository topicoRepository;

    @Transactional(readOnly = true)
    public List<QuestaoDTO> listarTodas() {
        return questaoRepository.findAll().stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<QuestaoDTO> listarPorTopico(Long topicoId) {
        return questaoRepository.findByTopicoId(topicoId).stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public QuestaoDTO criar(QuestaoDTO dto) {
        Topico topico = topicoRepository.findById(dto.getTopicoId())
                .orElseThrow(() -> new RuntimeException("Tópico não encontrado com ID: " + dto.getTopicoId()));

        Questao questao = Questao.builder()
                .enunciado(dto.getEnunciado())
                .peso(dto.getPeso())
                .topico(topico)
                .opcoes(dto.getOpcoes())
                .respostaCorreta(dto.getRespostaCorreta())
                .build();

        Questao salva = questaoRepository.save(questao);
        return converterParaDTO(salva);
    }

    @Transactional
    public QuestaoDTO atualizar(Long id, QuestaoDTO dto) {
        Questao questao = questaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Questão não encontrada com ID: " + id));

        Topico topico = topicoRepository.findById(dto.getTopicoId())
                .orElseThrow(() -> new RuntimeException("Tópico não encontrado com ID: " + dto.getTopicoId()));

        questao.setEnunciado(dto.getEnunciado());
        questao.setPeso(dto.getPeso());
        questao.setTopico(topico);
        questao.setOpcoes(dto.getOpcoes());
        questao.setRespostaCorreta(dto.getRespostaCorreta());

        Questao atualizada = questaoRepository.save(questao);
        return converterParaDTO(atualizada);
    }

    @Transactional
    public void deletar(Long id) {
        if (!questaoRepository.existsById(id)) {
            throw new RuntimeException("Questão não encontrada com ID: " + id);
        }
        questaoRepository.deleteById(id);
    }

    private QuestaoDTO converterParaDTO(Questao q) {
        QuestaoDTO dto = new QuestaoDTO();
        dto.setId(q.getId());
        dto.setEnunciado(q.getEnunciado());
        dto.setPeso(q.getPeso());
        dto.setTopicoId(q.getTopico().getId());
        dto.setNomeTopico(q.getTopico().getNome());
        dto.setOpcoes(q.getOpcoes());
        dto.setRespostaCorreta(q.getRespostaCorreta());
        return dto;
    }
}
