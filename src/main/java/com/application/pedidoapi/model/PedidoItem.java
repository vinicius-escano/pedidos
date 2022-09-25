package com.application.pedidoapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tb_pedido_item")
public class PedidoItem{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "codigo_pedido",foreignKey = @ForeignKey(name = "fk_pedidoitem_pedido"))
    private Pedido pedido;

    @OneToOne
    @JoinColumn(name = "codigo_produto", foreignKey = @ForeignKey(name = "fk_produto_pedidoitem"))
    private Produto produto;

    @Column(name = "quantidade_solicitada")
    private double quantidadeSolicitada;

    @Column(name = "valor_total")
    private double valorTotal;

    @Column(name = "valor_cobrado_unidade")
    private double valorPorUnidadeCobrada;

    public PedidoItem(Produto produto, double qtdeSolicitada){
        this.produto = produto;
        this.quantidadeSolicitada = qtdeSolicitada;
        this.valorPorUnidadeCobrada = produto.getValorVenda();
        this.valorTotal = qtdeSolicitada * produto.getValorVenda();
    }
}