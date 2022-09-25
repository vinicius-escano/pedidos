package com.application.pedidoapi.util;

import com.application.pedidoapi.enums.SituacaoPedido;
import com.application.pedidoapi.enums.Tipo;
import com.application.pedidoapi.model.Pedido;
import com.application.pedidoapi.model.PedidoItem;
import com.application.pedidoapi.model.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestUtil {

    private static UUID CODIGO_PRODUTO;
    private static final String FABRICANTE_PRODUTO = "Metallica";
    private static final String NOME_DESCRICAO_PRODUTO = "Parafuso 5/16";
    private static final double VALOR_COMPRA_PRODUTO = 0.15;
    private static final double VALOR_VENDA_PRODUTO = 1.50;
    private static final double QUANTIDADE_DISPONIVEL_PRODUTO = 10.0;

    private static UUID CODIGO_SERVICO;
    private static final String FORNECEDOR_SERVICO = "Kleiton Marcenaria";
    private static final String NOME_PRODUTO_SERVICO = "Serralheria";
    private static final double VALOR_COMPRA_SERVICO = 0.0;
    private static final double VALOR_VENDA_SERVICO = 50.00;
    private static final double QUANTIDADE_DISPONIVEL_SERVICO = 0.0;
    private static final int CODIGO_ISS = 123;
    private static final double ALIQUOTA = 1.85;

    public static Produto getProduto(Tipo tipoItem, boolean ativo) {
        Produto produto = Produto.builder()
                .id(new UUID(1, 2))
                .fabricanteFornecedor(FABRICANTE_PRODUTO)
                .nomeDescricao(NOME_DESCRICAO_PRODUTO)
                .tipo(tipoItem)
                .valorCompra(VALOR_COMPRA_PRODUTO)
                .valorVenda(VALOR_VENDA_PRODUTO)
                .quantidadeDisponivel(QUANTIDADE_DISPONIVEL_PRODUTO)
                .ativo(true)
                .build();
        return produto;
    }

    public static Produto getServico(Tipo tipo, boolean ativo) {
        Produto servico = Produto.builder().fabricanteFornecedor(FORNECEDOR_SERVICO)
                .nomeDescricao(NOME_PRODUTO_SERVICO)
                .valorCompra(VALOR_COMPRA_SERVICO)
                .tipo(tipo)
                .valorVenda(VALOR_VENDA_SERVICO)
                .quantidadeDisponivel(QUANTIDADE_DISPONIVEL_SERVICO)
                .ativo(true)
                .codigoISS(CODIGO_ISS)
                .aliquota(ALIQUOTA)
                .build();
        return servico;
    }


    public static List<Produto> getItemList(int listSize) {
        List<Produto> produtos = new ArrayList<>();
        for (int i = 0; i < listSize; i++) {
            produtos.add(getProduto(Tipo.PRODUTO, true));
        }

        return produtos;
    }

    public static List<Produto> getItemInativoList(int listSize) {
        List<Produto> produtos = new ArrayList<>();
        for (int i = 0; i < listSize; i++) {
            produtos.add(getProduto(Tipo.PRODUTO, false));
        }

        return produtos;
    }

    public static Page<Produto> getItemPage(int listSize, int page) {
        Pageable pageable = PageRequest.of(page, listSize);
        return new PageImpl<>(getItemList(listSize), pageable, listSize);
    }

    public static Pedido getPedido(int quantidadeItens) {
        Pedido pedido = new Pedido();
        pedido.setPedidoSituacao(SituacaoPedido.EM_ABERTO);
        pedido.setCpfCnpjComprador("123.456.789-10");
        pedido.setDesconto(5.0);
        pedido.setId(new UUID(2, 1));
        pedido.setCadastradoEm(LocalDateTime.now());
        pedido.setValorTotal(130.24);
        pedido.setValorProdutos(65.40);
        pedido.setValorServicos(70.32);
        pedido.setItensPedido(getPedidoItemList(quantidadeItens));
        return pedido;
    }

    public static List<Pedido> getPedidoList(int listSize) {
        List<Pedido> pedidoList = new ArrayList<>();
        for (int i = 0; i < listSize; i++) {
            pedidoList.add(getPedido(2));
        }

        return pedidoList;
    }

    public static Page<Pedido> getPedidoPage(int listSize, int page) {
        Pageable pageable = PageRequest.of(page, listSize);
        return new PageImpl<>(getPedidoList(listSize), pageable, listSize);
    }

    public static PedidoItem getPedidoItem() {
        PedidoItem pedidoItem = new PedidoItem();
        pedidoItem.setId(new UUID(3, 1));
        pedidoItem.setProduto(getProduto(Tipo.PRODUTO, true));
        pedidoItem.setQuantidadeSolicitada(3.0);
        pedidoItem.setValorPorUnidadeCobrada(25.10);
        pedidoItem.setValorTotal(75.30);
        return pedidoItem;
    }

    public static List<PedidoItem> getPedidoItemList(int listSize) {
        List<PedidoItem> pedidoItens = new ArrayList<>();
        for (int i = 0; i < listSize; i++) {
            pedidoItens.add(getPedidoItem());
        }

        return pedidoItens;
    }

    public static Page<PedidoItem> getPedidoItemPage(int listSize, int page) {
        Pageable pageable = PageRequest.of(page, listSize);
        return new PageImpl<>(getPedidoItemList(listSize), pageable, listSize);
    }
}
