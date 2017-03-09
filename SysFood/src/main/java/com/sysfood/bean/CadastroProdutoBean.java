package com.sysfood.bean;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.sysfood.bo.ProdutoBo;
import com.sysfood.exception.NegocioException;
import com.sysfood.model.Produto;
import com.sysfood.model.SecaoProduto;
import com.sysfood.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroProdutoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Produto produto;

	@Inject
	private ProdutoBo produtoBo;

	public CadastroProdutoBean() {
		produto = new Produto();
	}

	public void salvar() {
		try {
			this.produto = produtoBo.salvar(this.produto);
			produto = new Produto();

			FacesUtil.addInfoMessage("Produto salvo com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public SecaoProduto[] getSecao(){
		return SecaoProduto.values();
	}

	public boolean isEditando() {
		return this.getProduto().getId() != null;
	}

	public Produto getProduto() {
		return produto == null ? new Produto() : produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

}
