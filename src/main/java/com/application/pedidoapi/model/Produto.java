package com.application.pedidoapi.model;

import com.application.pedidoapi.enums.Tipo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.type.UUIDCharType;

import javax.persistence.*;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    private double valorCompra;

    @Column(name = "valor_venda", nullable = false)
    private double valorVenda;

    @Column(name = "quantidade_disponivel")
    private double quantidadeDisponivel;

    @Column(name = "ativo")
    private boolean ativo = true;


}
