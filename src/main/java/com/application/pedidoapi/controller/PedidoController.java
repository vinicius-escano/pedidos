package com.application.pedidoapi.controller;

import com.application.pedidoapi.enums.Tipo;
import com.application.pedidoapi.model.Pedido;
import com.application.pedidoapi.model.PedidoItem;
import com.application.pedidoapi.model.Produto;
import com.application.pedidoapi.model.Servico;
import com.application.pedidoapi.repository.ProdutoRepository;
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
    private ProdutoService produtoService;

    @Autowired
    private ServicoService servicoService;

    @GetMapping("/novoPedido")
    public ModelAndView novoPedido(HttpServletRequest httpServletRequest){
        Pedido pedido = new Pedido();
        httpServletRequest.getSession().setAttribute("pedido", pedido);
        return new ModelAndView("novo-pedido").addObject("pedido", pedido).addObject("itensEncontrados", new ArrayList<>());
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
            PedidoItem pedidoItem = new PedidoItem(produtoAdicionar.get());
            pedidoItem.setQuantidadeSolicitada(qtdeSolicitada);
            pedidoItem.setValorTotal(qtdeSolicitada * pedidoItem.getValorPorUnidadeCobrada());
            pedido.getItensPedido().add(pedidoItem);
            return new ModelAndView("novo-pedido").addObject("itensEncontrados", new ArrayList<>()).addObject("pedido", pedido);
        }
        return new ModelAndView("home");
    }
}
