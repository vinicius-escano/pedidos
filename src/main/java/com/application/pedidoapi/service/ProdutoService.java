package com.application.pedidoapi.service;

import com.application.pedidoapi.model.Pedido;
import com.application.pedidoapi.model.Produto;
import com.application.pedidoapi.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    public Optional<Produto> save(Produto produto) {
        Optional<Produto> opProduto = Optional.ofNullable(produtoRepository.save(produto));
        if (opProduto.isPresent()) {
            return opProduto;
        }
        return Optional.empty();
    }

    public Optional<Produto> findById(UUID id) {
        return produtoRepository.findById(id);
    }

    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    public Page<Produto> findAll(Pageable pageable) {
        return produtoRepository.findAll(pageable);
    }

    public List<Produto> findByDescricao(String descricao) {
        return produtoRepository.findByDescricao(descricao);
    }

    public Page<Produto> findAllByDescricaoPageable(String nomeDescricao, Pageable pageable) {
        return produtoRepository.findByDescricaoPageable(nomeDescricao, pageable);
    }

    public Optional<Produto> update(Produto pedido) {
        Optional<Produto> opProduto = Optional.ofNullable(produtoRepository.saveAndFlush(pedido));
        if (opProduto.isPresent()) {
            return opProduto;
        }
        return Optional.empty();
    }
    public boolean delete(Produto produto) {
        produtoRepository.delete(produto);
        Optional<Produto> deleteValidation = findById(produto.getId());
        if(!deleteValidation.isPresent()){
            return true;
        }
        return false;
    }

    public void alteraEstoque(Pedido pedido) {
        pedido.getItensPedido().stream().forEach(item -> {
            Optional<Produto> opProduto = produtoRepository.findById(item.getProduto().getId());
            if (opProduto.isPresent()) {
                if (!(item.getQuantidadeSolicitada() > opProduto.get().getQuantidadeDisponivel())) {
                    opProduto.get().setQuantidadeDisponivel(opProduto.get().getQuantidadeDisponivel() - item.getQuantidadeSolicitada());
                    produtoRepository.save(opProduto.get());
                }
            }
        });
    }



}
