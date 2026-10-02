package com.questionarioConex.avaliacao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Map;

@Data
public class SubmissaoAvaliacaoDTO {

    @NotBlank(message = "O nome do candidato é obrigatório")
    private String nomeCandidato;

    // Chave: ID da Questão, Valor: Índice da resposta escolhida
    @NotNull(message = "As respostas são obrigatórias")
    private Map<Long, Integer> respostas;
}