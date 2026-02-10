package com.application.pedidoapi.dto;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.application.pedidoapi.enums.Tipo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProdutoDTO {

    @Id
    private UUID id;
    private Tipo tipo;
    private String nomeDescricao;
    private String fabricanteFornecedor;
    private Double valorCompra;
    private Double valorVenda;
    private Double quantidadeDisponivel;
    private boolean ativo = true;
    private Integer codigoISS;
    private Double aliquota = 0.0;

}

