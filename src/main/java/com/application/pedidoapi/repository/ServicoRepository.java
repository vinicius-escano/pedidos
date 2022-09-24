package com.application.pedidoapi.repository;

import com.application.pedidoapi.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, UUID> {

    @Query("select s from Servico s where s.nomeDescricao like %:descricao%")
    List<Servico> findAllByDescricao(@Param("descricao") String descricao);
}
