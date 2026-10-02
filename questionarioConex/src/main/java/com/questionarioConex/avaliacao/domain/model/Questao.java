package com.questionarioConex.avaliacao.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Questao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String enunciado;

    @Column(nullable = false)
    private Integer peso; // Peso configurável da questão

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topico_id", nullable = false)
    private Topico topico;

    @ElementCollection
    @CollectionTable(name = "questao_opcoes", joinColumns = @JoinColumn(name = "questao_id"))
    @Column(name = "opcao_texto")
    @Builder.Default
    private List<String> opcoes = new ArrayList<>();

    @Column(nullable = false)
    private Integer respostaCorreta; // Índice da resposta certa (0, 1, 2, 3)

    public boolean isRespostaCorreta(Integer respostaUsuario) {
        return this.respostaCorreta != null && this.respostaCorreta.equals(respostaUsuario);
    }
}