package com.questionarioConex.avaliacao.service;

import com.questionarioConex.avaliacao.domain.model.Topico;
import com.questionarioConex.avaliacao.domain.repository.TopicoRepository;
import com.questionarioConex.avaliacao.dto.TopicoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TopicoService {

    private final TopicoRepository topicoRepository;

    @Transactional(readOnly = true)
    public List<TopicoDTO> listarTodos() {
        return topicoRepository.findAll().stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TopicoDTO buscarPorId(Long id) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tópico não encontrado com ID: " + id));
        return converterParaDTO(topico);
    }

    @Transactional
    public TopicoDTO criar(TopicoDTO dto) {
        if (topicoRepository.findByNome(dto.getNome()).isPresent()) {
            throw new RuntimeException("Já existe um tópico com este nome.");
        }

        Topico topico = Topico.builder()
                .nome(dto.getNome())
                .build();

        Topico salvo = topicoRepository.save(topico);
        return converterParaDTO(salvo);
    }

    @Transactional
    public TopicoDTO atualizar(Long id, TopicoDTO dto) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tópico não encontrado com ID: " + id));

        topico.setNome(dto.getNome());
        Topico atualizado = topicoRepository.save(topico);
        return converterParaDTO(atualizado);
    }

    @Transactional
    public void deletar(Long id) {
        if (!topicoRepository.existsById(id)) {
            throw new RuntimeException("Tópico não encontrado com ID: " + id);
        }
        topicoRepository.deleteById(id);
    }

    private TopicoDTO converterParaDTO(Topico topico) {
        return TopicoDTO.builder()
                .id(topico.getId())
                .nome(topico.getNome())
                .quantidadeQuestoes(topico.getQuestoes() != null ? topico.getQuestoes().size() : 0)
                .build();
    }
}

