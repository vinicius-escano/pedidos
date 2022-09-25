package com.application.pedidoapi.service;

import com.application.pedidoapi.enums.SituacaoPedido;
import com.application.pedidoapi.enums.Tipo;
import com.application.pedidoapi.model.Pedido;
import com.application.pedidoapi.model.PedidoItem;
import com.application.pedidoapi.repository.PedidoJPARepository;
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
    PedidoJPARepository pedidoJPARepository;

    @Autowired
    private PedidoItemService pedidoItemService;

    @Autowired
    private ProdutoService produtoService;

    public Optional<Pedido> save(Pedido pedido){
        Optional<Pedido> opPedido = Optional.ofNullable(pedidoJPARepository.save(pedido));
        if(opPedido.isPresent()){
            return opPedido;
        }
        return Optional.empty();
    }

    public Optional<Pedido> update(Pedido pedido) {
        return save(pedido);
    }

    public List<Pedido> findAll(){
        return pedidoJPARepository.findAll();
    }

    public Optional<Pedido> findById(UUID uuid){
        return pedidoJPARepository.findById(uuid);
    }

    public Page<Pedido> findAll(Pageable pageable) {
        return pedidoJPARepository.findAll(pageable);
    }

    public Page<Pedido> findAllByStatus(SituacaoPedido situacaoPedido, Pageable pageable) {
        return pedidoJPARepository.findBySituacaoPedidoEquals(situacaoPedido, pageable);
    }

    public boolean delete(Pedido pedido) {
        pedidoItemService.delete(pedido.getItensPedido());
        pedidoJPARepository.delete(pedido);
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
        Pedido saved = pedidoJPARepository.save(principal);
        pedido.getItensPedido().stream().forEach(item -> {
            item.setPedido(saved);
        });
        List<PedidoItem> savedItens = pedidoItemService.saveAll(pedido.getItensPedido());
        saved.getItensPedido().addAll(savedItens);
        return saved;
    }

    public Pedido updateConfirmado(Pedido pedido) {
        pedido.setPedidoSituacao(SituacaoPedido.CONFIRMADO);
        return pedidoJPARepository.save(pedido);
    }

    public Pedido updateCancelado(Pedido pedido) {
        pedido.setPedidoSituacao(SituacaoPedido.CANCELADO);
        return pedidoJPARepository.save(pedido);
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
        Optional<Pedido> opPedido = pedidoJPARepository.findById(UUID.fromString(uuid));
        if(opPedido.isPresent()){
            List<PedidoItem> itens = pedidoItemService.findAllByPedidoId(opPedido.get());
            if(!itens.isEmpty()){
                opPedido.get().getItensPedido().addAll(itens);
            }
        }
        return opPedido;
    }

    public Pedido confirmaPedido(Pedido pedido) {
        produtoService.alteraEstoque(pedido);
        Pedido savedPedido = updateConfirmado(pedido);
        List<PedidoItem> itens = pedidoItemService.findAllByPedidoId(savedPedido);
        itens.stream().forEach(i -> {
            savedPedido.getItensPedido().add(i);
        });
        return savedPedido;
    }

    /*public Optional<ModelAndView> buscaListaAlterarLimite() {

    }

    public void filtraLista(List<Pedido> listPedido, String cpfCnpj, String dataSolicitadoInicio, String dataSolicitadoFim, SituacaoPedido status) {
        List<Pedido> listaFiltrada = new ArrayList<>();
        List<Pedido> listUtil = new ArrayList<>();
        if (dataSolicitado != null && !dataSolicitado.equals("")) {
            LocalDate data = ConverterUtil.ldFormatter(dataSolicitado);
            for (Pedido p: listPedido) {
                if (p.getDataSolicitacao().isEqual(data)) {
                    listaFiltrada.add(lm);
                }
            }
        }
        if (nomeCliente != null && !nomeCliente.equals("")) {
            if (listaFiltrada.size() == 0) {
                for (AlteraLimite lm : listSolicitacoes) {
                    if (lm.getNomeCliente().substring(0, lm.getNomeCliente().indexOf(" ")).equalsIgnoreCase(nomeCliente)) {
                        listaFiltrada.add(lm);
                    }
                }
            } else {
                for (AlteraLimite lm : listaFiltrada) {
                    if (lm.getNomeCliente().substring(0, lm.getNomeCliente().indexOf(" ")).equalsIgnoreCase(nomeCliente)) {
                        listUtil.add(lm);
                    }
                }
                listaFiltrada.clear();
                listaFiltrada.addAll(listUtil);
            }
        }
        if (status != null && !status.equals("")) {
            StatusAprovacaoEnum aprovacaoEnum = null;
            if (status.equals("EM_ANALISE")) {
                aprovacaoEnum = StatusAprovacaoEnum.EM_ANALISE;
            } else if (status.equals("APROVADO")) {
                aprovacaoEnum = StatusAprovacaoEnum.APROVADO;
            } else if (status.equals("REPROVADO")) {
                aprovacaoEnum = StatusAprovacaoEnum.RECUSADO;
            }
            if (listaFiltrada.size() == 0) {
                for (AlteraLimite lm : listSolicitacoes) {
                    if (lm.getStatus() == aprovacaoEnum) {
                        listaFiltrada.add(lm);
                    }
                }
            } else {
                listUtil.clear();
                for (AlteraLimite lm : listaFiltrada) {
                    if (lm.getStatus() == aprovacaoEnum) {
                        listUtil.add(lm);
                    }
                }
                listaFiltrada.clear();
                listaFiltrada.addAll(listUtil);
            }
        }
        alteraLimiteDTO.getListAlteraLimite().clear();
        alteraLimiteDTO.getListAlteraLimite().addAll(listaFiltrada);
    }
*/

}
