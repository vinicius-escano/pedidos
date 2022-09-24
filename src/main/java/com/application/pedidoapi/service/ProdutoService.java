package com.application.pedidoapi.service;

import com.application.pedidoapi.model.Pedido;
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

    public void alteraEstoque(Pedido pedido) {
        pedido.getItensPedido().stream().forEach(item ->{
            Optional<Produto> opProduto = produtoRepository.findById(item.getProduto().getId());
            if(opProduto.isPresent()) {
                if (!(item.getQuantidadeSolicitada() > opProduto.get().getQuantidadeDisponivel())) {
                    opProduto.get().setQuantidadeDisponivel(opProduto.get().getQuantidadeDisponivel() - item.getQuantidadeSolicitada());
                    produtoRepository.save(opProduto.get());
                }
            }
        });
    }
}
