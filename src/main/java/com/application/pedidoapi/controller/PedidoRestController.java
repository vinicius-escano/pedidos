package com.application.pedidoapi.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.application.pedidoapi.model.Pedido;
import com.application.pedidoapi.service.PedidoService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoRestController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<Pedido> create(@RequestBody Pedido pedido) {
        Optional<Pedido> saved = pedidoService.save(pedido);
        return saved.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> getById(@PathVariable UUID id) {
        Optional<Pedido> op = pedidoService.findById(id);
        return op.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> list(@RequestParam(required = false, defaultValue = "0") int page) {
        List<Pedido> list = pedidoService.findAll();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> update(@PathVariable UUID id, @RequestBody Pedido pedido) {
        pedido.setId(id);
        Optional<Pedido> updated = pedidoService.update(pedido);
        return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        Optional<Pedido> op = pedidoService.findById(id);
        if (op.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        boolean deleted = pedidoService.delete(op.get());
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
