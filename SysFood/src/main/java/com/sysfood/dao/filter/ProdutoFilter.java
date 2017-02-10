package com.sysfood.dao.filter;

import java.io.Serializable;

public class ProdutoFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nome;
	private Boolean status;
	private Integer quantidadeEstoque;
	private Boolean controlarEstoque;

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

	public Integer getQuantidadeEstoque() {
		return quantidadeEstoque;
	}

	public void setQuantidadeEstoque(Integer quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}

	public Boolean getControlarEstoque() {
		return controlarEstoque;
	}

	public void setControlarEstoque(Boolean controlarEstoque) {
		this.controlarEstoque = controlarEstoque;
	}

}
