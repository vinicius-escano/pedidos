package com.application.pedidoapi.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "codigo")
	private Integer codigo;
    
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