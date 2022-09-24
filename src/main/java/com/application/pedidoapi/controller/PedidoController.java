package com.application.pedidoapi.controller;

import com.application.pedidoapi.model.Pedido;
import com.application.pedidoapi.model.PedidoItem;
import com.application.pedidoapi.model.Produto;
import com.application.pedidoapi.repository.ProdutoRepository;
import com.application.pedidoapi.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class PedidoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/novoPedido")
    public ModelAndView novoPedido(HttpServletRequest httpServletRequest){
        Pedido pedido = new Pedido();
        httpServletRequest.getSession().setAttribute("pedido", pedido);
        return new ModelAndView("pedido").addObject("pedido", pedido).addObject("itensEncontrados", new ArrayList<>());
    }

    @RequestMapping(value = "/efetivar", params = {"descricaoItem"})
    public ModelAndView buscaItem(@RequestParam("descricaoItem") String descricaoItem, HttpServletRequest httpServletRequest){
        Pedido pedido = (Pedido) httpServletRequest.getSession().getAttribute("pedido");
        List<Produto> encontrados = produtoService.findByDescricao(descricaoItem);
        if(!encontrados.isEmpty()){
            return new ModelAndView("pedido").addObject("itensEncontrados", encontrados).addObject("pedido", pedido);
        }
        return new ModelAndView("home");
    }

    @RequestMapping(value = "/efetivar", params = {"addItem"})
    public ModelAndView adicionaItem(@RequestParam("codigo") UUID id, @RequestParam("qtdeSolicitada") double qtdeSolicitada, HttpServletRequest httpServletRequest){
        Pedido pedido = (Pedido) httpServletRequest.getSession().getAttribute("pedido");
        Optional<Produto> produtoAdicionar = produtoService.findById(id);
        if(produtoAdicionar.isPresent()){
            PedidoItem pedidoItem = new PedidoItem(produtoAdicionar.get());
            pedidoItem.setQuantidadeSolicitada(qtdeSolicitada);
            pedidoItem.setValorTotal(qtdeSolicitada * pedidoItem.getValorPorUnidadeCobrada());
            pedido.getItens().add(pedidoItem);
            return new ModelAndView("pedido").addObject("itensEncontrados", new ArrayList<>()).addObject("pedido", pedido);
        }
        return new ModelAndView("home");
    }
}
