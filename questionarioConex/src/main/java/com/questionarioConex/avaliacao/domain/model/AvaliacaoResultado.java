package com.questionarioConex.avaliacao.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "avaliacao_resultados")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvaliacaoResultado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidato_id", nullable = false)
    private Candidato candidato;

    @Column(nullable = false)
    private Integer pontosObtidos;

    @Column(nullable = false)
    private Integer pontosPossiveis;

    @Column(nullable = false)
    private Double porcentagem;

    @Column(nullable = false)
    private String nivelClassificacao;

    @Column(nullable = false)
    private LocalDateTime dataRealizacao;

    @PrePersist
    public void prePersist() {
        this.dataRealizacao = LocalDateTime.now();
    }
}