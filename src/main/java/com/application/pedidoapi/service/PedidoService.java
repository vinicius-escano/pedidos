package com.application.pedidoapi.service;

import com.application.pedidoapi.enums.SituacaoPedido;
import com.application.pedidoapi.enums.Tipo;
import com.application.pedidoapi.model.Pedido;
import com.application.pedidoapi.model.PedidoItem;
import com.application.pedidoapi.model.Produto;
import com.application.pedidoapi.repository.PedidoRepository;
import com.application.pedidoapi.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private PedidoItemService pedidoItemService;

    public Optional<Pedido> save(Pedido pedido){
        Optional<Pedido> opPedido = Optional.ofNullable(pedidoRepository.save(pedido));
        if(opPedido.isPresent()){
            return opPedido;
        }
        return Optional.empty();
    }

    public Optional<Pedido> update(Pedido pedido) {
        return save(pedido);
    }

    public List<Pedido> findAll(){
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> findById(UUID uuid){
        return pedidoRepository.findById(uuid);
    }

    public Page<Pedido> findAll(Pageable pageable) {
        return pedidoRepository.findAll(pageable);
    }

    public Page<Pedido> findAllByStatus(SituacaoPedido situacaoPedido, Pageable pageable) {
        return pedidoRepository.findBySituacaoPedidoEquals(situacaoPedido, pageable);
    }

    public boolean delete(Pedido pedido) {
        pedidoItemService.delete(pedido.getItensPedido());
        pedidoRepository.delete(pedido);
        return true;
    }

    public Pedido savePreConfirmacao(Pedido pedido) {
        Pedido principal = new Pedido();
        principal.setDesconto(pedido.getDesconto());
        principal.setValorServicos(pedido.getValorServicos());
        principal.setValorProdutos(pedido.getValorProdutos());
        principal.setValorTotal(pedido.getValorTotal());
        principal.setPedidoSituacao(SituacaoPedido.EM_ABERTO);
        principal.setCadastradoEm(LocalDateTime.now());
        Pedido saved = pedidoRepository.save(principal);
        pedido.getItensPedido().stream().forEach(item -> {
            item.setPedido(saved);
        });
        List<PedidoItem> savedItens = pedidoItemService.saveAll(pedido.getItensPedido());
        saved.getItensPedido().addAll(savedItens);
        return saved;
    }

    public Pedido updateConfirmado(Pedido pedido) {
        pedido.setPedidoSituacao(SituacaoPedido.CONFIRMADO);
        return pedidoRepository.save(pedido);
    }

    public Pedido updateCancelado(Pedido pedido) {
        pedido.setPedidoSituacao(SituacaoPedido.CANCELADO);
        return pedidoRepository.save(pedido);
    }

    public boolean aplicaDesconto(Pedido pedido, double percentual) {
        List<PedidoItem> itensAplicarDesconto = pedido.getItensPedido().stream().filter(ip -> ip.getProduto().getTipo().equals(Tipo.PRODUTO)).collect(Collectors.toList());
        if (!itensAplicarDesconto.isEmpty()) {
            pedido.setDesconto(percentual);
            AtomicReference<Double> totalProdutos = new AtomicReference<>(0.0);
            itensAplicarDesconto.stream().forEach(i -> {
                totalProdutos.updateAndGet(t -> t += i.getValorTotal());
            });
            Double valorDescontar = (totalProdutos.get() * percentual) / 100;
            if(valorDescontar < totalProdutos.get()){
                pedido.setValorTotal(pedido.getValorTotal() - valorDescontar);
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    public void calculaValoresTiposPedidos(Pedido pedido) {
        List<PedidoItem> produtos = pedido.getItensPedido().stream().filter(ip -> ip.getProduto().getTipo().equals(Tipo.PRODUTO)).collect(Collectors.toList());
        if(!produtos.isEmpty()){
            produtos.stream().forEach(p ->{
                pedido.setValorProdutos(pedido.getValorProdutos() + p.getValorTotal());
            });
        }
        List<PedidoItem> servicos = pedido.getItensPedido().stream().filter(ip -> ip.getProduto().getTipo().equals(Tipo.SERVICO)).collect(Collectors.toList());
        if(!servicos.isEmpty()){
            servicos.stream().forEach(s ->{
                pedido.setValorServicos(pedido.getValorServicos() + s.getValorTotal());
            });
        }
    }

    public Optional<Pedido> buscaMontaPedidoVisualizacao(String uuid) {
        Optional<Pedido> opPedido = pedidoRepository.findById(UUID.fromString(uuid));
        if(opPedido.isPresent()){
            List<PedidoItem> itens = pedidoItemService.findAllByPedidoId(opPedido.get());
            if(!itens.isEmpty()){
                opPedido.get().getItensPedido().addAll(itens);
            }
        }
        return opPedido;
    }


}
