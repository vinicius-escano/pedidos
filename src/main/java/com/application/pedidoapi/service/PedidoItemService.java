package com.application.pedidoapi.service;

import com.application.pedidoapi.model.Pedido;
import com.application.pedidoapi.model.PedidoItem;
import com.application.pedidoapi.model.Produto;
import com.application.pedidoapi.repository.PedidoItemJPARepository;
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
    PedidoItemJPARepository pedidoItemJPARepository;

    @Autowired
    PedidoItemRepository pedidoItemRepository;

    public Optional<PedidoItem> save(PedidoItem pedidoItem){
        Optional<PedidoItem> opPedidoItem = Optional.ofNullable(pedidoItemJPARepository.save(pedidoItem));
        if(opPedidoItem.isPresent()){
            return opPedidoItem;
        }
        return Optional.empty();
    }

    public List<PedidoItem> saveAll(List<PedidoItem> pedidoItens){
        return pedidoItemJPARepository.saveAll(pedidoItens);
    }

    public List<PedidoItem> findAllByPedidoId(Pedido pedido) {
        return pedidoItemJPARepository.findAllByPedidoId(pedido);
    }

    public Optional<PedidoItem> update(PedidoItem pedidoItem) {
        return save(pedidoItem);
    }

    public Optional<PedidoItem> findById(UUID uuid) {
        Optional<PedidoItem> opPedidoItem = pedidoItemJPARepository.findById(uuid);
        if (opPedidoItem.isEmpty()) {
            return opPedidoItem;
        }

        opPedidoItem.get().setPedido(opPedidoItem.get().getPedido());
        return opPedidoItem;
    }


    public Page<PedidoItem> findAll(Pageable pageable) {
        return pedidoItemJPARepository.findAll(pageable);
    }

    public List<PedidoItem> findAll() {
        return pedidoItemJPARepository.findAll();
    }


    public boolean delete(PedidoItem pedidoItem) {
        pedidoItemJPARepository.delete(pedidoItem);
        return true;
    }

    public boolean delete(List<PedidoItem> pedidoItens) {
        for(PedidoItem pi: pedidoItens){
            pedidoItemJPARepository.delete(pi);
        }
        return true;
    }

    public List<PedidoItem> findAllWithProduto(Produto produto) {
        return pedidoItemJPARepository.findAllWithProduto(produto);
    }

    public List<PedidoItem> findPedidoItemByProdutoId(UUID uuid){
        return pedidoItemRepository.findPedidoItemByProdutoId(uuid);
    }
}
