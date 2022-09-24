package com.application.pedidoapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @GetMapping("/")
    public ModelAndView home(){
        return new ModelAndView("home");
    }

    @GetMapping("/produtos")
    public ModelAndView listaProdutos(){
        return new ModelAndView("produtos-servicos-menu");
    }

}
