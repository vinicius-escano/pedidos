package com.application.pedidoapi.repository;

import com.application.pedidoapi.enums.SituacaoPedido;
import com.application.pedidoapi.model.Pedido;
import com.application.pedidoapi.model.PedidoItem;
import com.application.pedidoapi.model.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PedidoItemJPARepository extends JpaRepository<PedidoItem, UUID> {

    @Query("select pi from PedidoItem pi where pi.pedido = :id")
    List<PedidoItem> findAllByPedidoId(@Param("id") Pedido pedido);

    @Query("select pi from PedidoItem pi where pi.produto = :id")
    List<PedidoItem> findAllWithProduto(@Param("id") Produto produto);

    @Query("select pi from PedidoItem pi where pi.quantidadeSolicitada = :quantidade")
    Page<PedidoItem> findAllPorQuantidade(@Param("quantidade") double quantidade, Pageable pageable);

}
