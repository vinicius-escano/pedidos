package com.application.pedidoapi.model;

import com.application.pedidoapi.dto.ProdutoDTO;
import com.application.pedidoapi.enums.Tipo;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.type.UUIDCharType;

import javax.persistence.*;
import java.util.UUID;

@SqlResultSetMapping(name = "ProdutoResults", classes = {
        @ConstructorResult(targetClass = ProdutoDTO.class, columns = {
                @ColumnResult(name = "ID", type = UUID.class),
                @ColumnResult(name = "TIPO", type = Tipo.class),
                @ColumnResult(name = "NOME_DESCRICAO", type = String.class),
                @ColumnResult(name = "FABRICANTE_FORNECEDOR", type = String.class),
                @ColumnResult(name = "VALOR_COMPRA", type = Double.class),
                @ColumnResult(name = "VALOR_VENDA", type = Double.class),
                @ColumnResult(name = "QUANTIDADE_DISPONIVEL", type = Double.class),
                @ColumnResult(name = "CODIGO_ISS", type = Integer.class),
                @ColumnResult(name = "ALIQUOTA", type = Double.class),
                @ColumnResult(name = "ATIVO", type = Double.class)})})
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

    @Column(name = "ativo")
    private boolean ativo = true;

    @Column(name = "codigo_iss")
    private Integer codigoISS;

    @Column(name = "aliquota")
    private Double aliquota = 0.0;

}
