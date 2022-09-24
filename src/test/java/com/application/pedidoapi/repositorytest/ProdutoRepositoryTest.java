package com.application.pedidoapi.repositorytest;

import com.application.pedidoapi.model.Produto;
import com.application.pedidoapi.repository.ProdutoRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@ContextConfiguration(locations = "/application-context-test.xml")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProdutoRepositoryTest {

    private static final UUID CODIGO = UUID.randomUUID();
    private static final String FABRICANTE = "Metallica";
    private static final String NOME_PRODUTO = "Parafuso 5/16";
    private static final double VALOR_COMPRA = 0.15;
    private static final double VALOR_VENDA = 1.50;
    private static final double QUANTIDADE_DISPONIVEL = 10.0;

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    @Order(1)
    public void cleanUp() {
        jdbcTemplate.execute("TRUNCATE TABLE tb_produtos");
    }

    @Test
    @Order(2)
    public void saveProduto() {
        Produto produto = Produto.builder()
                .codigo(CODIGO)
                .fabricante(FABRICANTE)
                .nomeDescricao(NOME_PRODUTO)
                .valorCompra(VALOR_COMPRA)
                .valorVenda(VALOR_VENDA)
                .quantidadeDisponivel(QUANTIDADE_DISPONIVEL)
                .ativo(true)
                .build();
        Produto savedProduto = produtoRepository.save(produto);
        Assertions.assertEquals(FABRICANTE, savedProduto.getFabricante());
        Assertions.assertEquals(NOME_PRODUTO, savedProduto.getNomeDescricao());
        Assertions.assertEquals(VALOR_COMPRA, savedProduto.getValorCompra());
        Assertions.assertEquals(VALOR_VENDA, savedProduto.getValorVenda());
        Assertions.assertEquals(QUANTIDADE_DISPONIVEL, savedProduto.getQuantidadeDisponivel());
        Assertions.assertEquals(true, savedProduto.isAtivo());
        System.out.print("Cadastrado com sucesso");
    }

    @Test
    @Order(3)
    public void findProduto() {
        Optional<Produto> opProduto = produtoRepository.findByUUID(CODIGO);
        if (opProduto.isPresent()) {
            Assertions.assertEquals(CODIGO, opProduto.get().getCodigo());
        }
        System.out.print("Encontrado com sucesso");
    }

    @Test
    @Order(4)
    public void getListProduto() {
        List<Produto> listProdutos = produtoRepository.findAll();
        Assertions.assertEquals(2, listProdutos.size());
        System.out.print("Encontrado lista com sucesso");
    }

    @Test
    @Order(5)
    public void update() {
        Optional<Produto> opProduto = produtoRepository.findByUUID(CODIGO);
        if (opProduto.isPresent()) {
            opProduto.get().setAtivo(false);
        }
        Produto produtoUpdated = produtoRepository.save(opProduto.get());
        Assertions.assertEquals(FABRICANTE, produtoUpdated.getFabricante());
        Assertions.assertEquals(NOME_PRODUTO, produtoUpdated.getNomeDescricao());
        Assertions.assertEquals(VALOR_COMPRA, produtoUpdated.getValorCompra());
        Assertions.assertEquals(VALOR_VENDA, produtoUpdated.getValorVenda());
        Assertions.assertEquals(QUANTIDADE_DISPONIVEL, produtoUpdated.getQuantidadeDisponivel());
        Assertions.assertEquals(false, produtoUpdated.isAtivo());
        System.out.print("Atualizado com sucesso");
    }

    @Test
    @Order(6)
    public void delete() {
        Optional<Produto> opProduto = produtoRepository.findByUUID(CODIGO);
        if (opProduto.isPresent()) {
            produtoRepository.delete(opProduto.get());
        }
        Produto deletedProduto = null;
        Optional<Produto> opDeleted = produtoRepository.findByUUID(CODIGO);
        if (opDeleted.isPresent()) {
            deletedProduto = opDeleted.get();
        }
        Assertions.assertNull(deletedProduto);
        System.out.print("Deletado com sucesso");
    }

}
