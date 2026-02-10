package com.application.pedidoapi.enums;

public enum TipoMedida {

	METRICO("METRICO"), IMPERIAL("IMPERIAL");
	
	private String sigla;

	TipoMedida(String sigla) {
		this.sigla = sigla;
	}

	public String getSigla() {
		return sigla;
	}
	
}
