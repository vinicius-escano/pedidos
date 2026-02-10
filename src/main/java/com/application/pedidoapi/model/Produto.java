package com.application.pedidoapi.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.application.pedidoapi.enums.Tipo;
import com.application.pedidoapi.enums.UnidadeMedida;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_produto")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @Column(name = "nome_descricao")
    private String nomeDescricao;

    @Column(name = "fabricante_fornecedor")
    private String fabricanteFornecedor;

    @Column(name = "valor_compra", nullable = false)
    private Double valorCompra;

    @Column(name = "valor_venda", nullable = false)
    private Double valorVenda;

    @Column(name = "quantidade_disponivel")
    private Double quantidadeDisponivel;
    
    @Column(name = "unidade_medida")
    @Enumerated(EnumType.STRING)
    private UnidadeMedida unidadeMedida;

    @Column(name = "ativo")
    private boolean ativo = true;

    @Column(name = "codigo_iss")
    private Integer codigoISS;

    @Column(name = "aliquota")
    private Double aliquota = 0.0;

}
