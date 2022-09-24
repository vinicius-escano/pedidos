package com.application.pedidoapi.repository;

import com.application.pedidoapi.model.Pedido;
import com.application.pedidoapi.model.PedidoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PedidoItemRepository extends JpaRepository<PedidoItem, UUID> {

    @Query("select pi from PedidoItem pi where pi.pedido = :id")
    List<PedidoItem> findAllByPedidoId(@Param("id") Pedido pedido);

}
