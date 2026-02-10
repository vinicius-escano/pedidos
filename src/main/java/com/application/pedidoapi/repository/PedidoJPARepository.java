package com.application.pedidoapi.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.application.pedidoapi.enums.SituacaoPedido;
import com.application.pedidoapi.model.Pedido;

@Repository
public interface PedidoJPARepository extends JpaRepository<Pedido, UUID> {

    @Query("select p from Pedido p where p.pedidoSituacao = :pedidoSituacao")
    Page<Pedido> findBySituacaoPedidoEquals(@Param("pedidoSituacao") SituacaoPedido pedidoSituacao, Pageable pageable);


}
