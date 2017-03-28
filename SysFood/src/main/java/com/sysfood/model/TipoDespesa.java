package com.sysfood.model;

public enum TipoDespesa {

	FUNCIONARIO("Funcionário"), GAS("Gás"), AGUA("Água"), LUZ("Luz"), PASSAGEM("Passagem"), FUNCIONARIO_EXTRA(
			"Funcionário extra"), ALUGUEL("Aluguel"), EVENTOS("Evento"), PRODUTO("Produto");

	private TipoDespesa(String descricao) {
		this.descricao = descricao;
	}

	private String descricao;

	public String getDescricao() {
		return descricao;
	}

}
