package com.application.pedidoapi.repository;

import com.application.pedidoapi.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, UUID> {

    @Query("select p from Produto p where p.nomeDescricao like %:descricao%")
    List<Produto> findByDescricao(@Param("descricao") String descricao);

}
