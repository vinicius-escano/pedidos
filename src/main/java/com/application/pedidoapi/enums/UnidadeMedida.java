package com.application.pedidoapi.enums;

public enum UnidadeMedida {

	UNIDADE("UN"),
	QUILOGRAMA("KG"),
	LITRO("L"),
	METRO("M"),
	PACOTE("PACOTE"),
	CAIXA("CAIXA"),
	ROLO("ROLO"),
	FRASCO("FRASCO"),
	SACO("SACO"),
	BANDEJA("BANDEJA");

	private String sigla;

	UnidadeMedida(String sigla) {
		this.sigla = sigla;
	}

	public String getSigla() {
		return sigla;
	}
}
