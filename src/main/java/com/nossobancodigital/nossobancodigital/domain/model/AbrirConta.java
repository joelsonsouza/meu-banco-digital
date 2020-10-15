package com.nossobancodigital.nossobancodigital.domain.model;

import java.util.Arrays;

public enum AbrirConta {
	ACEITAR("Aceitar"), RECUSAR("Recusar");

	private String descricao;

	AbrirConta(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}
}