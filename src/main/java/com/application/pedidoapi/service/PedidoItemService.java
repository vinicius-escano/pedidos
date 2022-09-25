package com.application.pedidoapi.service;

import com.application.pedidoapi.model.Pedido;
import com.application.pedidoapi.model.PedidoItem;
import com.application.pedidoapi.repository.PedidoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PedidoItemService {

    @Autowired
    PedidoItemRepository pedidoItemRepository;

    public Optional<PedidoItem> save(PedidoItem pedidoItem){
        Optional<PedidoItem> opPedidoItem = Optional.ofNullable(pedidoItemRepository.save(pedidoItem));
        if(opPedidoItem.isPresent()){
            return opPedidoItem;
        }
        return Optional.empty();
    }

    public List<PedidoItem> saveAll(List<PedidoItem> pedidoItens){
        return pedidoItemRepository.saveAll(pedidoItens);
    }

    public List<PedidoItem> findAllByPedidoId(Pedido pedido) {
        return pedidoItemRepository.findAllByPedidoId(pedido);
    }

    public Optional<PedidoItem> update(PedidoItem pedidoItem) {
        return save(pedidoItem);
    }

    public Optional<PedidoItem> findById(UUID uuid) {
        Optional<PedidoItem> opPedidoItem = pedidoItemRepository.findById(uuid);
        if (opPedidoItem.isEmpty()) {
            return opPedidoItem;
        }

        opPedidoItem.get().setPedido(opPedidoItem.get().getPedido());
        return opPedidoItem;
    }


    public Page<PedidoItem> findAll(Pageable pageable) {
        return pedidoItemRepository.findAll(pageable);
    }


    public boolean delete(PedidoItem pedidoItem) {
        pedidoItemRepository.delete(pedidoItem);
        return true;
    }

    public boolean delete(List<PedidoItem> pedidoItens) {
        pedidoItemRepository.deleteAll(pedidoItens);
        return true;
    }
}
