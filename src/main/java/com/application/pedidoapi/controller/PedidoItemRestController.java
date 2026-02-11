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
import com.application.pedidoapi.model.PedidoItem;
import com.application.pedidoapi.service.PedidoItemService;
import com.application.pedidoapi.service.PedidoService;

@RestController
@RequestMapping("/api/pedido-items")
public class PedidoItemRestController {

    @Autowired
    private PedidoItemService pedidoItemService;

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoItem> create(@RequestBody PedidoItem pedidoItem) {
        Optional<PedidoItem> saved = pedidoItemService.save(pedidoItem);
        return saved.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoItem> getById(@PathVariable UUID id) {
        Optional<PedidoItem> op = pedidoItemService.findById(id);
        return op.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<PedidoItem>> list(@RequestParam(required = false, defaultValue = "0") int page) {
        List<PedidoItem> list = pedidoItemService.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/pedido/{pedidoUuid}")
    public ResponseEntity<List<PedidoItem>> listByPedido(@PathVariable UUID pedidoUuid) {
        Optional<Pedido> opPedido = pedidoService.findById(pedidoUuid);
        if (opPedido.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<PedidoItem> itens = pedidoItemService.findAllByPedidoId(opPedido.get());
        return ResponseEntity.ok(itens);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoItem> update(@PathVariable UUID id, @RequestBody PedidoItem pedidoItem) {
        pedidoItem.setId(id);
        Optional<PedidoItem> updated = pedidoItemService.update(pedidoItem);
        return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        Optional<PedidoItem> op = pedidoItemService.findById(id);
        if (op.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        boolean deleted = pedidoItemService.delete(op.get());
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().build();
    }
}