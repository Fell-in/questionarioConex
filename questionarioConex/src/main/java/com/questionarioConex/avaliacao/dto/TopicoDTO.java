package com.questionarioConex.avaliacao.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopicoDTO {

    private Long id;

    @NotBlank(message = "O nome do tópico é obrigatório")
    private String nome;

    private Integer quantidadeQuestoes;
}