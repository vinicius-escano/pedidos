package com.application.pedidoapi.dto;

import com.application.pedidoapi.enums.Tipo;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.type.UUIDCharType;

import javax.persistence.*;
import java.util.UUID;


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

