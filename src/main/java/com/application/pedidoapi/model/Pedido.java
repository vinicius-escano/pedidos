package com.application.pedidoapi.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.application.pedidoapi.enums.SituacaoPedido;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "cpf_cpnj_comprador")
    private String cpfCnpjComprador;

    @Column(name = "desconto")
    private double desconto = 0.0;

    @Column(name = "valor_servicos")
    private double valorServicos = 0.0;

    @Column(name = "valor_produtos")
    private double valorProdutos = 0.0;

    @Column(name = "valor_total")
    private double valorTotal = 0.0;

    @Column(name = "situacao_pedido")
    @Enumerated(EnumType.STRING)
    private SituacaoPedido pedidoSituacao = SituacaoPedido.EM_ABERTO;

    @Column(name = "cadastrado_em")
    private LocalDateTime cadastradoEm;

    @Transient
    private List<PedidoItem> itensPedido = new ArrayList<>();

}