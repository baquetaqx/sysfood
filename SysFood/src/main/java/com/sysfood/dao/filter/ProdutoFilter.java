package com.sysfood.dao.filter;

import java.io.Serializable;

import com.sysfood.model.SecaoProduto;

public class ProdutoFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nome;
	private Boolean status;
	private SecaoProduto secao;

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

	public SecaoProduto getSecao() {
		return secao;
	}

	public void setSecao(SecaoProduto secao) {
		this.secao = secao;
	}

}
