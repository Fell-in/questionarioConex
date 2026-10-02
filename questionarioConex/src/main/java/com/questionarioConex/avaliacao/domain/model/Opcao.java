package com.questionarioConex.avaliacao.domain.model;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Opcao {

    private Integer ordem;
    private String texto;
}