package com.application.pedidoapi.controller;

import com.application.pedidoapi.model.PedidoItem;
import com.application.pedidoapi.service.PedidoItemService;
import com.application.pedidoapi.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("pedido-item")
public class PedidoItemController {

    @Autowired
    private PedidoItemService pedidoItemService;

    @PostMapping("/save")
    ResponseEntity<PedidoItem> save(@RequestBody @Valid PedidoItem pedidoItem) {
        Optional<PedidoItem> opPedidoItem = pedidoItemService.save(pedidoItem);
        if (opPedidoItem.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(opPedidoItem.get());
    }

    @PutMapping("/update")
    ResponseEntity<PedidoItem> update(@RequestBody @Valid PedidoItem pedidoItem) {
        Optional<PedidoItem> opPedidoItem = pedidoItemService.update(pedidoItem);
        if (opPedidoItem.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(opPedidoItem.get());
    }

    @GetMapping("/{id}")
    ResponseEntity<PedidoItem> findById(@PathVariable UUID id) {
        Optional<PedidoItem> opPedidoItem = pedidoItemService.findById(id);
        if (opPedidoItem.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(opPedidoItem.get());
    }

    @GetMapping("/all/{page}")
    ResponseEntity<List<PedidoItem>> findAll(@PathVariable int page) {
        Page<PedidoItem> pedidoItemPage;
        pedidoItemPage = pedidoItemService.findAll(PageUtil.getPageable(page));
        return ResponseEntity.ok(pedidoItemPage.getContent());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<PedidoItem> delete(@PathVariable UUID id) {
        Optional<PedidoItem> opPedidoItem = pedidoItemService.findById(id);
        if (opPedidoItem.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        boolean deleted = pedidoItemService.delete(opPedidoItem.get());
        if (deleted) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.badRequest().build();
    }
}
