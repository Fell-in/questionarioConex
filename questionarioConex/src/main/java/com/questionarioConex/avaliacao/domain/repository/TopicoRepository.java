package com.questionarioConex.avaliacao.domain.repository;

import com.questionarioConex.avaliacao.domain.model.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {
    Optional<Topico> findByNome(String nome);
}