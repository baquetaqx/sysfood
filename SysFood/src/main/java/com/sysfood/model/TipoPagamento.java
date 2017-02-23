package com.sysfood.model;

public enum TipoPagamento {
	DINHEIRO("Dinheiro"), CARTAO_CREDITO("Cartão de crédito"), CARTAO_DEBITO("Cartão de débito");

	private String descricao;

	TipoPagamento(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
