package com.application.pedidoapi.service;

import com.application.pedidoapi.model.Pedido;
import com.application.pedidoapi.model.PedidoItem;
import com.application.pedidoapi.repository.PedidoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PedidoItemService {

    @Autowired
    PedidoItemRepository pedidoItemRepository;

    public void save(PedidoItem pedidoItem){
        pedidoItemRepository.save(pedidoItem);
    }

    public List<PedidoItem> saveAll(List<PedidoItem> pedidoItens){
        return pedidoItemRepository.saveAll(pedidoItens);
    }

    public List<PedidoItem> findAllByPedidoId(Pedido pedido) {
        return pedidoItemRepository.findAllByPedidoId(pedido);
    }
}
