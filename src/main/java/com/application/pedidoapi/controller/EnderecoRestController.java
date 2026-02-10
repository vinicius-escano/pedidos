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

import com.application.pedidoapi.model.Endereco;
import com.application.pedidoapi.repository.EnderecoJPARepository;

@RestController
@RequestMapping("/api/enderecos")
public class EnderecoRestController {

    @Autowired
    private EnderecoJPARepository enderecoJPARepository;

    @PostMapping
    public ResponseEntity<Endereco> create(@RequestBody Endereco endereco) {
        Endereco saved = enderecoJPARepository.save(endereco);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Endereco> getById(@PathVariable UUID id) {
        Optional<Endereco> op = enderecoJPARepository.findById(id);
        return op.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Endereco>> list() {
        List<Endereco> list = enderecoJPARepository.findAll();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Endereco> update(@PathVariable UUID id, @RequestBody Endereco endereco) {
        if (!enderecoJPARepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        endereco.setId(id);
        Endereco updated = enderecoJPARepository.save(endereco);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        if (!enderecoJPARepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        enderecoJPARepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
