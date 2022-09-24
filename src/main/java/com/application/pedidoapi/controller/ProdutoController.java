package com.application.pedidoapi.controller;

import com.application.pedidoapi.enums.Tipo;
import com.application.pedidoapi.model.Produto;
import com.application.pedidoapi.model.Servico;
import com.application.pedidoapi.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/cadastrarproduto")
    public ModelAndView novoProduto(){
        return new ModelAndView("produto-servico").addObject("produto", new Produto()).addObject("servico", new Servico());
    }

    @GetMapping("/listarprodutos")
    public ModelAndView listarProdutos(){
        List<Produto> listProdutos = produtoService.findAll();
        return new ModelAndView("estoque").addObject("isProduto", true).addObject("produtos", listProdutos);
    }

    @PostMapping("/novoproduto")
    public ModelAndView cadastrarProduto(Produto produto){
        produtoService.save(produto);
        return new ModelAndView("home");
    }




}
