package com.application.pedidoapi.model;

import com.application.pedidoapi.enums.SituacaoPedido;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "tb_pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "desconto")
    private double desconto = 0.0;

    @Column(name = "situacao_pedido")
    @Enumerated(EnumType.STRING)
    private SituacaoPedido pedidoSituacao = SituacaoPedido.EM_ABERTO;

    @OneToMany
    @JoinColumn(name = "codigo_produto", foreignKey = @ForeignKey(name = "fk_item_pedido"))
    private List<PedidoItem> itens = new ArrayList<>();

}