package com.application.pedidoapi.controller;

import com.application.pedidoapi.enums.SituacaoPedido;
import com.application.pedidoapi.enums.Tipo;
import com.application.pedidoapi.exception.SessaoExpiradaException;
import com.application.pedidoapi.model.Pedido;
import com.application.pedidoapi.model.PedidoItem;
import com.application.pedidoapi.model.Produto;
import com.application.pedidoapi.service.PedidoItemService;
import com.application.pedidoapi.service.PedidoService;
import com.application.pedidoapi.service.ProdutoService;
import com.application.pedidoapi.utils.DataErrorHandler;
import com.application.pedidoapi.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private PedidoItemService pedidoItemService;

    @PostMapping
    ResponseEntity<Pedido> save(@RequestBody @Valid Pedido pedido) {
        Optional<Pedido> opPedido = pedidoService.save(pedido);
        if (opPedido.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(opPedido.get());
    }

    @PutMapping
    ResponseEntity<Pedido> update(@RequestBody @Valid Pedido pedido) {
        Optional<Pedido> opPedido = pedidoService.update(pedido);
        if (opPedido.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(opPedido.get());
    }

    @GetMapping("/{id}")
    ResponseEntity<Pedido> findById(@PathVariable UUID id) {
        Optional<Pedido> opPedido = pedidoService.findById(id);
        if (opPedido.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(opPedido.get());
    }

    @GetMapping("/all/{page}")
    ResponseEntity<List<Pedido>> findAllPaginated(@PathVariable int page, @RequestParam(required = false) SituacaoPedido situacao) {
        Page<Pedido> pedidosPage;
        if (situacao == null) {
            pedidosPage = pedidoService.findAll(PageUtil.getPageable(page));
            return ResponseEntity.ok(pedidosPage.getContent());
        }

        pedidosPage = pedidoService.findAllByStatus(situacao, PageUtil.getPageable(page));
        return ResponseEntity.ok(pedidosPage.getContent());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Pedido> delete(@PathVariable UUID id) {
        Optional<Pedido> opPedido = pedidoService.findById(id);
        if (opPedido.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        boolean deleted = pedidoService.delete(opPedido.get());
        if (deleted) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.badRequest().build();
    }


    //mapping front
    @GetMapping("/novoPedido")
    public ModelAndView novoPedido(HttpServletRequest httpServletRequest) {
        Pedido pedido = new Pedido();
        httpServletRequest.getSession().setAttribute("pedido", pedido);
        return new ModelAndView("novo-pedido").addObject("pedido", pedido).addObject("itensEncontrados", new ArrayList<>());
    }

    @GetMapping("/visualizarpedidos")
    public ModelAndView visualizarPedidos() {
        List<Pedido> pedidos = pedidoService.findAll();
        return new ModelAndView("lista-pedidos").addObject("pedidos", pedidos);
    }

    @GetMapping("/acessarpedido")
    public ModelAndView acessarPedido(@RequestParam("id") String id, HttpServletRequest httpServletRequest) {
        Optional<Pedido> opPedido = pedidoService.buscaMontaPedidoVisualizacao(id);
        if (opPedido.isPresent()) {
            httpServletRequest.getSession().setAttribute("pedido", opPedido.get());
            return new ModelAndView("visualizacao-pedido").addObject("pedido", opPedido.get());
        }
        return null;
    }

    @GetMapping(value = "/buscaitem")
    public ModelAndView buscaItem(@RequestParam("descricao") String descricaoItem, @RequestParam("tipo") String tipoItem, HttpServletRequest httpServletRequest) {
        Optional<Pedido> opPedido = Optional.ofNullable((Pedido) httpServletRequest.getSession().getAttribute("pedido"));
        if (opPedido.isPresent()) {
            List<Produto> pEncontrados;
            if (tipoItem.toUpperCase().equals(Tipo.PRODUTO.toString())) {
                pEncontrados = produtoService.findProdutoByDescricao(descricaoItem);
            } else {
                pEncontrados = produtoService.findServicoByDescricao(descricaoItem);
            }
            if (!pEncontrados.isEmpty()) {
                List<Produto> filteredList = pEncontrados.stream().filter(p -> p.isAtivo() && p.getQuantidadeDisponivel() > 0.0).collect(Collectors.toList());
                httpServletRequest.getSession().setAttribute("itensEncontrados", filteredList);
                return new ModelAndView("novo-pedido").addObject("itensEncontrados", filteredList).addObject("pedido", opPedido.get());
            }
            return new ModelAndView("novo-pedido").addObject("itensEncontrados", new ArrayList<>()).addObject("pedido", opPedido.get());
        }
        throw new SessaoExpiradaException("Sessão expirada, reinicie o processo");
    }

    @GetMapping(value = "/addprodutoservico")
    public ModelAndView adicionaItem(@RequestParam("index") Integer index, @RequestParam("qtde") double qtdeSolicitada, HttpServletRequest httpServletRequest) {
        Optional<Pedido> opPedido = Optional.ofNullable((Pedido) httpServletRequest.getSession().getAttribute("pedido"));
        if (opPedido.isPresent()) {
            List<Produto> encontrados = (List<Produto>) httpServletRequest.getSession().getAttribute("itensEncontrados");
            Optional<Produto> produtoAdicionar = produtoService.findById(encontrados.get(index).getId());
            if (produtoAdicionar.isPresent()) {
                PedidoItem pedidoItem = new PedidoItem(produtoAdicionar.get(), qtdeSolicitada);
                opPedido.get().getItensPedido().add(pedidoItem);
                opPedido.get().setValorTotal(opPedido.get().getValorTotal() + pedidoItem.getValorTotal());
                return new ModelAndView("novo-pedido").addObject("pedido", opPedido.get()).addObject("itensEncontrados", new ArrayList<>());
            }
            return new ModelAndView("home");
        }
        throw new SessaoExpiradaException("Sessão expirada, reinicie o processo");
    }

    @GetMapping(value = "/removeprodutoservico")
    public ModelAndView adicionaItem(@RequestParam("id") String id, HttpServletRequest httpServletRequest) {
        Optional<Pedido> opPedido = Optional.ofNullable((Pedido) httpServletRequest.getSession().getAttribute("pedido"));
        if (opPedido.isPresent()) {
            UUID uuid = UUID.fromString(id);
            Optional<PedidoItem> itemRemover = opPedido.get().getItensPedido().stream().filter(ip -> ip.getProduto().getId().equals(uuid)).findFirst();
            if (itemRemover.isPresent()) {
                opPedido.get().getItensPedido().remove(itemRemover.get());
                opPedido.get().setValorTotal(opPedido.get().getValorTotal() - itemRemover.get().getValorTotal());
                return new ModelAndView("novo-pedido").addObject("pedido", opPedido.get()).addObject("itensEncontrados", new ArrayList<>());
            }
            return new ModelAndView("home");
        }
        throw new SessaoExpiradaException("Sessão expirada, por gentileza refaça o processo");
    }

    @PostMapping("/efetivar")
    public ModelAndView efetivarPedido(HttpServletRequest httpServletRequest) {
        Optional<Pedido> opPedido = Optional.ofNullable((Pedido) httpServletRequest.getSession().getAttribute("pedido"));
        if (opPedido.isPresent()) {
            if(opPedido.get().getItensPedido().isEmpty()){
                return DataErrorHandler.throwMessage("Não é possivel efetivar um pedido sem itens!");
            }
            pedidoService.calculaValoresTiposPedidos(opPedido.get());
            Pedido savedPedido = pedidoService.savePreConfirmacao(opPedido.get());
            httpServletRequest.getSession().setAttribute("pedido", savedPedido);
            return new ModelAndView("pedido-confirmacao").addObject("pedido", savedPedido);
        }
        throw new SessaoExpiradaException("Sessão expirada, por gentileza refaça o processo");
    }

    @GetMapping("/aplicadesconto")
    public ModelAndView aplicaDesconto(@RequestParam("perc") Double perc, HttpServletRequest httpServletRequest) {
        Optional<Pedido> opPedido = Optional.ofNullable((Pedido) httpServletRequest.getSession().getAttribute("pedido"));
        if (opPedido.isPresent()) {
            if (pedidoService.aplicaDesconto(opPedido.get(), perc)) {
                return new ModelAndView("pedido-confirmacao").addObject("pedido", opPedido.get());
            }
        }
        throw new SessaoExpiradaException("Sessão expirada, por gentileza refaça o processo");
    }

    @GetMapping("/removerdesconto")
    public ModelAndView aplicaDesconto(HttpServletRequest httpServletRequest) {
        Optional<Pedido> opPedido = Optional.ofNullable((Pedido) httpServletRequest.getSession().getAttribute("pedido"));
        if (opPedido.isPresent()) {
            opPedido.get().setValorTotal(opPedido.get().getValorServicos() + opPedido.get().getValorProdutos());
            opPedido.get().setDesconto(0.0);
            return new ModelAndView("pedido-confirmacao").addObject("pedido", opPedido.get());
        }
        throw new SessaoExpiradaException("Sessão expirada, por gentileza refaça o processo");
    }

    @PostMapping("/confirmapedido")
    public ModelAndView confirmaPedido(HttpServletRequest httpServletRequest) {
        Optional<Pedido> opPedido = Optional.ofNullable((Pedido) httpServletRequest.getSession().getAttribute("pedido"));
        if (opPedido.isPresent()) {
            Pedido savedPedido = pedidoService.confirmaPedido(opPedido.get());
            return new ModelAndView("pedido-finalizado").addObject("pedido", savedPedido);
        }
        throw new SessaoExpiradaException("Sessão expirada, por gentileza refaça o processo");
    }

    @GetMapping("/cancelapedido/{id}")
    public ModelAndView cancelaPedido(@PathVariable("id") String id) {
        Optional<Pedido> opPedido = pedidoService.findById(UUID.fromString(id));
        if (opPedido.isPresent()) {
            Pedido savedPedido = pedidoService.updateCancelado(opPedido.get());
            return new ModelAndView("pedido-finalizado").addObject("pedido", savedPedido);
        }
        throw new SessaoExpiradaException("Sessão expirada, por gentileza refaça o processo");
    }

    /*@PostMapping("/buscaAlteracoesLimitePorLoja")
    public ModelAndView buscaAlteracoesLimitePorLoja(String status, @Nullable String cpfCnpj,
                                                     @Nullable String dataSolicitadoInicio,
                                                     @Nullable String dataSolicitadoFim,
                                                     HttpServletRequest httpServletRequest) {
        if ((cpfCnpj == null || cpfCnpj.equals("")) && (dataSolicitadoInicio == null || dataSolicitadoInicio.equals("")) && (status == null || status.equals(""))) {
            return alteraLimiteService.buscaListaAlterarLimite(httpServletRequest, Optional.of(Integer.valueOf(lojaSelecionada)), false, Optional.empty(), Optional.empty(), Optional.empty()).get();
        } else {
            List<Pedido> pedidos = pedidoService.findAll();
            return new ModelAndView("lista-pedidos").addObject("pedidos", pedidos);
        }
    }*/

}
