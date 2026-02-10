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

import com.application.pedidoapi.model.Empresa;
import com.application.pedidoapi.repository.EmpresaJPARepository;

@RestController
@RequestMapping("/api/empresas")
public class EmpresaRestController {

    @Autowired
    private EmpresaJPARepository empresaJPARepository;

    @PostMapping
    public ResponseEntity<Empresa> create(@RequestBody Empresa empresa) {
        Empresa saved = empresaJPARepository.save(empresa);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empresa> getById(@PathVariable UUID id) {
        Optional<Empresa> op = empresaJPARepository.findById(id);
        return op.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Empresa>> list() {
        List<Empresa> list = empresaJPARepository.findAll();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empresa> update(@PathVariable UUID id, @RequestBody Empresa empresa) {
        if (!empresaJPARepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        empresa.setId(id);
        Empresa updated = empresaJPARepository.save(empresa);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        if (!empresaJPARepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        empresaJPARepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
