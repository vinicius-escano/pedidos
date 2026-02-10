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

import com.application.pedidoapi.model.Parametro;
import com.application.pedidoapi.repository.ParametroJPARepository;

@RestController
@RequestMapping("/api/parametros")
public class ParametroRestController {

    @Autowired
    private ParametroJPARepository parametroJPARepository;

    @PostMapping
    public ResponseEntity<Parametro> create(@RequestBody Parametro parametro) {
        Parametro saved = parametroJPARepository.save(parametro);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Parametro> getById(@PathVariable UUID id) {
        Optional<Parametro> op = parametroJPARepository.findById(id);
        return op.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Parametro>> list() {
        List<Parametro> list = parametroJPARepository.findAll();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Parametro> update(@PathVariable UUID id, @RequestBody Parametro parametro) {
        if (!parametroJPARepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        parametro.setId(id);
        Parametro updated = parametroJPARepository.save(parametro);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        if (!parametroJPARepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        parametroJPARepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
