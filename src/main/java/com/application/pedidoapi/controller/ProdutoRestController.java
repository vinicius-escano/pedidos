package com.application.pedidoapi.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.application.pedidoapi.model.Produto;
import com.application.pedidoapi.service.ProdutoService;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoRestController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<Produto> create(@RequestBody Produto produto) {
        Optional<Produto> saved = produtoService.save(produto);
        return saved.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> getById(@PathVariable UUID id) {
        Optional<Produto> op = produtoService.findById(id);
        return op.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Produto>> list(@RequestParam(required = false, defaultValue = "0") int page,
                                              @RequestParam(required = false) String descricao) {
        // simple paginated list - if descricao provided, delegate to service pageable method
        List<Produto> list;
        if (descricao != null) {
            list = produtoService.findAllByDescricaoPageable(descricao, PageRequest.of(page, 20)).getContent();
        } else {
            list = produtoService.findAll(PageRequest.of(page, 20)).getContent();
        }
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> update(@PathVariable UUID id, @RequestBody Produto produto) {
        produto.setId(id);
        Optional<Produto> updated = produtoService.update(produto);
        return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        Optional<Produto> op = produtoService.findById(id);
        if (op.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        boolean deleted = produtoService.delete(op.get(), null);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
