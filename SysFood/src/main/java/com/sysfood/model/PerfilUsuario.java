package com.sysfood.model;

public enum PerfilUsuario {
	ADMINISTRADOR("Administrador"), COLABORADOR("Colaborador");
	
	private String descricao;

	
	private PerfilUsuario(String descricao) {
		this.descricao = descricao;
	}
	
	
	public String getDescricao() {
		return descricao;
	}

	
	
}
