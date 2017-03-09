package com.sysfood.dao.filter;

import java.io.Serializable;

public class AdicionaisFiltro implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nome;
	private Boolean status;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

}
