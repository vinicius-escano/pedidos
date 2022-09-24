package com.application.pedidoapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
    @JoinColumn(name = "codigo_produto", foreignKey = @ForeignKey(name = "fk_produto_pedido"))
    private Produto produto;

    @Column(name = "quantidade_solicitada")
    private double quantidadeSolicitada;

    @Column(name = "valor_total")
    private double valorTotal;

    @Column(name = "valor_cobrado_unidade")
    private double valorPorUnidadeCobrada;

    public PedidoItem(Produto produto){
        this.produto = produto;
        this.valorPorUnidadeCobrada = produto.getValorVenda();
    }
}