package com.application.pedidoapi.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "tb_servico")
public class Servico extends Produto{

    @Column(name = "codigo_iss")
    private int codigoISS;

    @Column(name = "aliquota")
    private double aliquota = 0.0;
}
