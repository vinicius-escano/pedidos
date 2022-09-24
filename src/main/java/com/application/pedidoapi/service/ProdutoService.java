package com.application.pedidoapi.service;

import com.application.pedidoapi.model.Produto;
import com.application.pedidoapi.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    public Produto save(Produto produto){
        return produtoRepository.save(produto);
    }

    public Optional<Produto> findById(UUID id){
        return produtoRepository.findById(id);
    }

    public List<Produto> findAll(){
        return produtoRepository.findAll();
    }

    public List<Produto> findByDescricao(String descricao){
        return produtoRepository.findByDescricao(descricao);
    }
}
