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
import org.springframework.web.bind.annotation.RestController;

import com.application.pedidoapi.model.Cliente;
import com.application.pedidoapi.repository.ClienteJPARepository;

@RestController
@RequestMapping("/api/clientes")
public class ClienteRestController {

    @Autowired
    private ClienteJPARepository clienteJPARepository;

    @PostMapping
    public ResponseEntity<Cliente> create(@RequestBody Cliente cliente) {
        Cliente saved = clienteJPARepository.save(cliente);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getById(@PathVariable UUID id) {
        Optional<Cliente> op = clienteJPARepository.findById(id);
        return op.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> list() {
        List<Cliente> list = clienteJPARepository.findAll();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> update(@PathVariable UUID id, @RequestBody Cliente cliente) {
        if (!clienteJPARepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cliente.setId(id);
        Cliente updated = clienteJPARepository.save(cliente);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        if (!clienteJPARepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        clienteJPARepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
