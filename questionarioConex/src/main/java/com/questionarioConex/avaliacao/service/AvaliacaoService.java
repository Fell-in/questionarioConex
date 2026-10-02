package com.questionarioConex.avaliacao.service;

import com.questionarioConex.avaliacao.domain.model.Questao;
import com.questionarioConex.avaliacao.domain.repository.QuestaoRepository;
import com.questionarioConex.avaliacao.dto.ResultadoAvaliacaoDTO;
import com.questionarioConex.avaliacao.dto.SubmissaoAvaliacaoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AvaliacaoService {

    private final QuestaoRepository questaoRepository;
    private final DiscordNotificationService discordService;

    @Transactional(readOnly = true)
    public ResultadoAvaliacaoDTO processarAvaliacao(SubmissaoAvaliacaoDTO submissao) {
        List<Questao> questoes = questaoRepository.findAll();

        int pontosObtidos = 0;
        int pontosPossiveis = 0;

        Map<String, int[]> acumuladorTopico = new HashMap<>(); // [acertosPeso, totalPeso]

        for (Questao q : questoes) {
            int peso = q.getPeso() != null ? q.getPeso() : 1;
            pontosPossiveis += peso;

            String topicoNome = q.getTopico().getNome();
            acumuladorTopico.putIfAbsent(topicoNome, new int[]{0, 0});
            acumuladorTopico.get(topicoNome)[1] += peso;

            Integer respostaUsuario = submissao.getRespostas().get(q.getId());
            if (q.isRespostaCorreta(respostaUsuario)) {
                pontosObtidos += peso;
                acumuladorTopico.get(topicoNome)[0] += peso;
            }
        }

        double porcentagem = pontosPossiveis > 0 ? ((double) pontosObtidos / pontosPossiveis) * 100 : 0.0;
        String nivel = determinarNivel(porcentagem);

        Map<String, String> desempenhoTopicos = new HashMap<>();
        acumuladorTopico.forEach((topico, valores) -> {
            int acertos = valores[0];
            int total = valores[1];
            double pct = total > 0 ? ((double) acertos / total) * 100 : 0.0;
            desempenhoTopicos.put(topico, String.format("%d/%d (%.1f%%)", acertos, total, pct));
        });

        ResultadoAvaliacaoDTO resultado = ResultadoAvaliacaoDTO.builder()
                .candidato(submissao.getNomeCandidato())
                .pontosObtidos(pontosObtidos)
                .pontosPossiveis(pontosPossiveis)
                .porcentagem(Math.round(porcentagem * 10.0) / 10.0)
                .nivelClassificacao(nivel)
                .desempenhoPorTopico(desempenhoTopicos)
                .build();

        // Notifica via Webhook do Discord de forma assíncrona
        discordService.notificarResultado(resultado);

        return resultado;
    }

    private String determinarNivel(double porcentagem) {
        if (porcentagem >= 80.0) return "Avançado / Apto";
        if (porcentagem >= 50.0) return "Intermediário";
        return "Iniciante / Insuficiente";
    }
}