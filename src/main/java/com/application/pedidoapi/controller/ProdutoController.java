package com.application.pedidoapi.controller;

import com.application.pedidoapi.enums.Tipo;
import com.application.pedidoapi.exception.NotFoundException;
import com.application.pedidoapi.exception.SessaoExpiradaException;
import com.application.pedidoapi.model.PedidoItem;
import com.application.pedidoapi.model.Produto;
import com.application.pedidoapi.service.ProdutoService;
import com.application.pedidoapi.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
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
        List<PedidoItem> pendencias = new ArrayList<>();
        boolean deleted = produtoService.delete(opProduto.get(), pendencias);
        if (deleted && pendencias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.badRequest().build();
    }


    //mappings front
    @GetMapping("/cadastrarproduto")
    public ModelAndView novoProduto() {
        return new ModelAndView("produto-servico")
                .addObject("produto", new Produto())
                .addObject("servico", new Produto())
                .addObject("jaExiste", false)
                .addObject("isProduto", false)
                .addObject("isServico", false);
    }

    @GetMapping("/listarprodutos")
    public ModelAndView listarProdutos() {
        try {
            List<Produto> listProdutos = produtoService.findAll(Tipo.PRODUTO);
            return new ModelAndView("estoque").addObject("produtos", listProdutos);
        } catch (NotFoundException ex) {
            throw new NotFoundException("Recurso não encontrado");
        }
    }

    @GetMapping("/listarservicos")
    public ModelAndView listarServicos() {
        try {
            List<Produto> listProdutos = produtoService.findAll(Tipo.SERVICO);
            return new ModelAndView("estoque").addObject("produtos", listProdutos);
        } catch (NotFoundException ex) {
            throw new NotFoundException("Recurso não encontrado");
        }
    }

    @PostMapping("/novoproduto")
    public ModelAndView cadastrarProduto(Produto produto) {
        produtoService.save(produto);
        return new ModelAndView("home");
    }

    @GetMapping("/acessarproduto/{id}")
    public ModelAndView acessarProduto(@PathVariable("id") String id) {
        Optional<Produto> opProduto = produtoService.findById(UUID.fromString(id));
        if (opProduto.isPresent()) {
            return new ModelAndView("produto-servico")
                    .addObject("produto", opProduto.get())
                    .addObject("servico", new Produto())
                    .addObject("jaExiste", true)
                    .addObject("isProduto", true)
                    .addObject("isServico", false);
        }
        throw new SessaoExpiradaException("");
    }

    @PostMapping("/produto/updateprodutoservico")
    public ModelAndView updateProduto(Produto produto) {
        Optional<Produto> opProduto = produtoService.update(produto);
        if (opProduto.isPresent()) {
            return new ModelAndView("produto-servico")
                    .addObject("produto", opProduto.get())
                    .addObject("servico", new Produto())
                    .addObject("jaExiste", true)
                    .addObject("isProduto", true)
                    .addObject("isServico", false);
        }
        throw new SessaoExpiradaException("Erro ao realizar processo");
    }

    @GetMapping("/desativaproduto/{id}")
    public ModelAndView updateDesativaProduto(@PathVariable("id") String id) {
        Optional<Produto> opProduto = produtoService.findById(UUID.fromString(id));
        if(opProduto.isPresent()){
            opProduto.get().setAtivo(false);
        }
        Optional<Produto> updatedProduto = produtoService.update(opProduto.get());
        if (updatedProduto.isPresent()) {
            return new ModelAndView("produto-servico")
                    .addObject("produto", updatedProduto.get())
                    .addObject("servico", new Produto())
                    .addObject("jaExiste", true)
                    .addObject("isProduto", true)
                    .addObject("isServico", false);
        }
        throw new SessaoExpiradaException("Erro ao realizar processo");
    }

    @GetMapping("/ativarproduto/{id}")
    public ModelAndView updateAtivarProduto(@PathVariable("id") String id) {
        Optional<Produto> opProduto = produtoService.findById(UUID.fromString(id));
        if(opProduto.isPresent()){
            opProduto.get().setAtivo(true);
        }
        Optional<Produto> updatedProduto = produtoService.update(opProduto.get());
        if (updatedProduto.isPresent()) {
            return new ModelAndView("produto-servico")
                    .addObject("produto", updatedProduto.get())
                    .addObject("servico", new Produto())
                    .addObject("jaExiste", true)
                    .addObject("isProduto", true)
                    .addObject("isServico", false);
        }
        throw new SessaoExpiradaException("Erro ao realizar processo");
    }

    @GetMapping("/excluiproduto/{id}")
    public ModelAndView deleteProduto(@PathVariable("id") String id) {
        Optional<Produto> opProduto = produtoService.findById(UUID.fromString(id));
        if(opProduto.isPresent()) {
            List<PedidoItem> pendencia = new ArrayList<>();
            if (produtoService.delete(opProduto.get(), pendencia)) {
                return new ModelAndView("produto-delete-response")
                        .addObject("deleted", true)
                        .addObject("produto", new Produto());
            } else {
                return new ModelAndView("produto-delete-response")
                        .addObject("deleted", false)
                        .addObject("produto", opProduto.get())
                        .addObject("pendencias", pendencia);
            }
        }
        throw new SessaoExpiradaException("");
    }


}
