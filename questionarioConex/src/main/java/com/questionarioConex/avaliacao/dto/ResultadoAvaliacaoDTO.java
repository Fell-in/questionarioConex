package com.questionarioConex.avaliacao.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ResultadoAvaliacaoDTO {
    private String candidato;
    private Integer pontosObtidos;
    private Integer pontosPossiveis;
    private Double porcentagem;
    private String nivelClassificacao;
    private Map<String, String> desempenhoPorTopico;
}