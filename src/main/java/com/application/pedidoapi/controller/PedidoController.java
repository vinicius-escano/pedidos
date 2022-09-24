package com.application.pedidoapi.controller;

import com.application.pedidoapi.enums.Tipo;
import com.application.pedidoapi.model.Pedido;
import com.application.pedidoapi.model.PedidoItem;
import com.application.pedidoapi.model.Produto;
import com.application.pedidoapi.model.Servico;
import com.application.pedidoapi.service.PedidoService;
import com.application.pedidoapi.service.ProdutoService;
import com.application.pedidoapi.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping(value = "pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ServicoService servicoService;

    @GetMapping("/novoPedido")
    public ModelAndView novoPedido(HttpServletRequest httpServletRequest){
        Pedido pedido = new Pedido();
        httpServletRequest.getSession().setAttribute("pedido", pedido);
        return new ModelAndView("novo-pedido").addObject("pedido", pedido).addObject("itensEncontrados", new ArrayList<>());
    }

    @GetMapping("/visualizarpedidos")
    public ModelAndView visualizarPedidos(){
        List<Pedido> pedidos = pedidoService.findAll();
        return new ModelAndView("lista-pedidos").addObject("pedidos", pedidos);
    }

    @GetMapping("/acessarpedido")
    public ModelAndView acessarPedido(@RequestParam("id") String id){
        Optional<Pedido> opPedido = pedidoService.buscaMontaPedidoVisualizacao(id);
        if(opPedido.isPresent()){
            return new ModelAndView("visualizacao-pedido").addObject("pedido", opPedido.get());
        }
        return null;
    }

    @GetMapping(value = "/buscaitem")
    public ModelAndView buscaItem(@RequestParam("descricao") String descricaoItem, @RequestParam("tipo") String tipoItem, HttpServletRequest httpServletRequest){
        Pedido pedido = (Pedido) httpServletRequest.getSession().getAttribute("pedido");
        if(tipoItem.toUpperCase().equals(Tipo.PRODUTO.toString())) {
            List<Produto> pEncontrados = produtoService.findByDescricao(descricaoItem);
            if (!pEncontrados.isEmpty()) {
                httpServletRequest.getSession().setAttribute("itensEncontrados", pEncontrados);
                return new ModelAndView("novo-pedido").addObject("itensEncontrados", pEncontrados).addObject("pedido", pedido);
            }
            return new ModelAndView("home");
        } else {
            List<Servico> sEncontrados = servicoService.findByDescricao(descricaoItem);
            if (!sEncontrados.isEmpty()) {
                httpServletRequest.getSession().setAttribute("itensEncontrados", sEncontrados);
                return new ModelAndView("novo-pedido").addObject("itensEncontrados", sEncontrados).addObject("pedido", pedido);
            }
            return new ModelAndView("home");
        }
    }

    @GetMapping(value = "/addprodutoservico")
    public ModelAndView adicionaItem(@RequestParam("index") Integer index, @RequestParam("qtde") double qtdeSolicitada, HttpServletRequest httpServletRequest){
        Pedido pedido = (Pedido) httpServletRequest.getSession().getAttribute("pedido");
        List<Produto> encontrados = (List<Produto>) httpServletRequest.getSession().getAttribute("itensEncontrados");
        Optional<Produto> produtoAdicionar = produtoService.findById(encontrados.get(index).getId());
        if(produtoAdicionar.isPresent()){
            PedidoItem pedidoItem = new PedidoItem(produtoAdicionar.get(), qtdeSolicitada);
            pedido.getItensPedido().add(pedidoItem);
            pedido.setValorTotal(pedido.getValorTotal() + pedidoItem.getValorTotal());
            return new ModelAndView("novo-pedido").addObject("pedido", pedido).addObject("itensEncontrados", new ArrayList<>());
        }
        return new ModelAndView("home");
    }

    @GetMapping(value = "/removeprodutoservico")
    public ModelAndView adicionaItem(@RequestParam("id") String id, HttpServletRequest httpServletRequest){
        Pedido pedido = (Pedido) httpServletRequest.getSession().getAttribute("pedido");
        UUID uuid = UUID.fromString(id);
        Optional<PedidoItem> itemRemover = pedido.getItensPedido().stream().filter(ip -> ip.getProduto().getId().equals(uuid)).findFirst();
        if(itemRemover.isPresent()){
            pedido.getItensPedido().remove(itemRemover.get());
            pedido.setValorTotal(pedido.getValorTotal() - itemRemover.get().getValorTotal());
            return new ModelAndView("novo-pedido").addObject("pedido", pedido).addObject("itensEncontrados", new ArrayList<>());
        }
        return new ModelAndView("home");
    }

    @PostMapping("/efetivar")
    public ModelAndView efetivarPedido(HttpServletRequest httpServletRequest){
        Pedido pedido = (Pedido) httpServletRequest.getSession().getAttribute("pedido");
        pedidoService.calculaValoresTiposPedidos(pedido);
        pedido = pedidoService.savePreConfirmacao(pedido);
        httpServletRequest.getSession().setAttribute("pedido", pedido);
        return new ModelAndView("pedido-confirmacao").addObject("pedido", pedido);
    }

    @GetMapping("/aplicadesconto")
    public ModelAndView aplicaDesconto(@RequestParam("perc") Double perc, HttpServletRequest httpServletRequest){
        Pedido pedido = (Pedido) httpServletRequest.getSession().getAttribute("pedido");
        if(pedidoService.aplicaDesconto(pedido, perc)){
            return new ModelAndView("pedido-confirmacao").addObject("pedido", pedido);
        }
        return null;
    }

    @GetMapping("/removerdesconto")
    public ModelAndView aplicaDesconto(HttpServletRequest httpServletRequest){
        Pedido pedido = (Pedido) httpServletRequest.getSession().getAttribute("pedido");
        pedido.setValorTotal(pedido.getValorServicos() + pedido.getValorProdutos());
        pedido.setDesconto(0.0);
        return new ModelAndView("pedido-confirmacao").addObject("pedido", pedido);
    }

    @PostMapping("/confirmapedido")
    public ModelAndView confirmaPedido(HttpServletRequest httpServletRequest){
        Pedido pedido = (Pedido) httpServletRequest.getSession().getAttribute("pedido");
        pedido = pedidoService.updateConfirmado(pedido);
        produtoService.alteraEstoque(pedido);
        return new ModelAndView("pedido-finalizado").addObject("pedido", pedido);
    }

    @PostMapping("/cancelapedido")
    public ModelAndView cancelaPedido(HttpServletRequest httpServletRequest){
        Pedido pedido = (Pedido) httpServletRequest.getSession().getAttribute("pedido");
        pedido = pedidoService.updateCancelado(pedido);
        produtoService.alteraEstoque(pedido);
        return new ModelAndView("pedido-finalizado").addObject("pedido", pedido);
    }

}
