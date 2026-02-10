package com.application.pedidoapi.repository;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.application.pedidoapi.model.PedidoItem;
import com.application.pedidoapi.model.QPedidoItem;
import com.querydsl.jpa.impl.JPAQuery;

@Repository
public class PedidoItemRepository {

    EntityManager entityManager;

    final QPedidoItem qPedidoItem = QPedidoItem.pedidoItem;

    public PedidoItemRepository(EntityManager em) {
        this.entityManager = em;
    }

    public List<PedidoItem> findPedidoItemByProdutoId(UUID itemId) {
        JPAQuery<PedidoItem> jpaQuery = new JPAQuery<>(entityManager);
        return jpaQuery.select(qPedidoItem).from(qPedidoItem).where(qPedidoItem.produto.id.eq(itemId)).fetch();
    }

}
