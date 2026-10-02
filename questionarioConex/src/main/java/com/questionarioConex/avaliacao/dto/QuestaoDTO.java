package com.questionarioConex.avaliacao.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class QuestaoDTO {
    private Long id;

    @NotBlank(message = "O enunciado da questão é obrigatório")
    private String enunciado;

    @NotNull(message = "O peso da questão é obrigatório")
    @Min(value = 1, message = "O peso mínimo é 1")
    private Integer peso;

    @NotNull(message = "O ID do tópico é obrigatório")
    private Long topicoId;

    private String nomeTopico;

    private List<String> opcoes;

    @NotNull(message = "A indicação da resposta correta é obrigatória")
    private Integer respostaCorreta;
}