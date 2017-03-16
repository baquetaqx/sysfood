package com.sysfood.model;

public enum SecaoProduto {
	PASTEIS("Pastéis G"),MINI("Mini Pasteis") ,REFRIGERANTES("Refrigerantes"), SUCOS("Sucos"), AGUA("Água");
	
	private String descricao;

	
	private SecaoProduto(String descricao) {
		this.descricao = descricao;
	}
	
	
	public String getDescricao() {
		return descricao;
	}

	
	
}
