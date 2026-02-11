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
import javax.persistence.Enumerated;
import javax.persistence.EnumType;

import com.application.pedidoapi.enums.TipoMedida;
import com.application.pedidoapi.enums.TipoMoeda;

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
@Table(name = "tb_parametro")
public class Parametro {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column(name = "codigo")
	private Integer codigo;
	
	@OneToOne
	@JoinColumn(name = "empresa", foreignKey = @ForeignKey(name = "fk_parametro_empresa"))
	private Empresa empresa;
	
	@Column(name = "tipo_moeda")
	@Enumerated(EnumType.STRING)
	private TipoMoeda tipoMoeda;
	
	@Column(name = "tipo_medida")
	@Enumerated(EnumType.STRING)
	private TipoMedida tipoMedida;
	
}