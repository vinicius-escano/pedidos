package com.application.pedidoapi.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
@Table(name = "tb_cliente")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column(name = "codigo")
	private Integer codigo;

	@Column(name = "nome")
	private String nome;
	
	@Column(name = "nome_fantasia")
	private String nomeFantasia;
	
	@Column(name = "cpf_cnpj")
	private String cpfCnpj;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "telefone")
	private String telefone;
	
	@OneToOne
	@JoinColumn(name = "endereco", foreignKey = @ForeignKey(name = "fk_cliente_endereco"))
	private Endereco endereco;
}