package com.application.pedidoapi.controller;

import com.application.pedidoapi.enums.SituacaoPedido;
import com.application.pedidoapi.enums.Tipo;
import com.application.pedidoapi.exception.NotFoundException;
import com.application.pedidoapi.model.Produto;
import com.application.pedidoapi.model.Servico;
import com.application.pedidoapi.service.ProdutoService;
import com.application.pedidoapi.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping(value = "produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping("/save")
    ResponseEntity<Produto> save(@RequestBody @Valid Produto produto) {
        Optional<Produto> opProduto = produtoService.save(produto);
        if (opProduto.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(opProduto.get());
    }

    @PutMapping("/update")
    ResponseEntity<Produto> update(@RequestBody @Valid Produto pedido) {
        Optional<Produto> opProduto = produtoService.update(pedido);
        if (opProduto.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(opProduto.get());
    }

    @GetMapping("/{id}")
    ResponseEntity<Produto> findById(@PathVariable UUID id) {
        Optional<Produto> opProduto = produtoService.findById(id);
        if (opProduto.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(opProduto.get());
    }

    @GetMapping("/all/{page}")
    ResponseEntity<List<Produto>> findAllPaginated(@PathVariable int page, @RequestParam(required = false) String descricao) {
        Page<Produto> pedidosPage;
        if (descricao == null) {
            pedidosPage = produtoService.findAll(PageUtil.getPageable(page));
            return ResponseEntity.ok(pedidosPage.getContent());
        }

        pedidosPage = produtoService.findAllByDescricaoPageable(descricao, PageUtil.getPageable(page));
        return ResponseEntity.ok(pedidosPage.getContent());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Produto> delete(@PathVariable UUID id) {
        Optional<Produto> opProduto = produtoService.findById(id);
        if (opProduto.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        boolean deleted = produtoService.delete(opProduto.get());
        if (deleted) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.badRequest().build();
    }


    //mappings front
    @GetMapping("/cadastrarproduto")
    public ModelAndView novoProduto() {
        return new ModelAndView("produto-servico").addObject("produto", new Produto()).addObject("servico", new Servico());
    }

    @GetMapping("/listarprodutos")
    public ModelAndView listarProdutos() {
        try {
            List<Produto> listProdutos = produtoService.findAll();
            return new ModelAndView("estoque").addObject("isProduto", true).addObject("produtos", listProdutos);
        } catch (NotFoundException ex) {
            throw new NotFoundException("Recurso não encontrado");
        }
    }

    @PostMapping("/novoproduto")
    public ModelAndView cadastrarProduto(Produto produto) {
        produtoService.save(produto);
        return new ModelAndView("home");
    }


}
