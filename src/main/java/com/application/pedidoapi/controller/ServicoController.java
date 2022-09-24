package com.application.pedidoapi.controller;

import com.application.pedidoapi.model.Produto;
import com.application.pedidoapi.model.Servico;
import com.application.pedidoapi.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ServicoController {

    @Autowired
    private ServicoService servicoService;

    @GetMapping("/listarservicos")
    public ModelAndView listarServicos(){
        List<Servico> listServicos = servicoService.findAll();
        return new ModelAndView("estoque").addObject("isProduto", false).addObject("servicos", listServicos);
    }

    @PostMapping("/novoservico")
    public ModelAndView cadastrarServico(Servico servico){
        servicoService.save(servico);
        return new ModelAndView("home");
    }
}
