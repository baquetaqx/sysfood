package com.sysfood.model;

public enum SecaoProduto {
	PASTEIS("Pastéis"), REFRIGERANTES("Refrigerantes"), SUCOS("Sucos");
	
	private String descricao;

	
	private SecaoProduto(String descricao) {
		this.descricao = descricao;
	}
	
	
	public String getDescricao() {
		return descricao;
	}

	
	
}
