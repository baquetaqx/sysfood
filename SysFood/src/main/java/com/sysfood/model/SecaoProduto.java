package com.sysfood.model;

public enum SecaoProduto {
	PASTEIS("Pastéis"), MINI_PASTEIS("Mini Pastéis"), REFRIGERANTES("Refrigerantes"), SUCOS("Sucos"), AGUAS("Águas");

	private String descricao;

	private SecaoProduto(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
