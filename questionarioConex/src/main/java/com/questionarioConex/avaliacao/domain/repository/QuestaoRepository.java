package com.questionarioConex.avaliacao.domain.repository;

import com.questionarioConex.avaliacao.domain.model.Questao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestaoRepository extends JpaRepository<Questao, Long> {

    List<Questao> findByTopicoId(Long topicoId);

    @Query("SELECT SUM(q.peso) FROM Questao q")
    Integer calcularPesoTotalGeral();
}