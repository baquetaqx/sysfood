package com.sysfood.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.sysfood.bo.ProdutoBo;
import com.sysfood.dao.filter.ProdutoFilter;
import com.sysfood.exception.NegocioException;
import com.sysfood.model.Produto;
import com.sysfood.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaProdutosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Produto> produtosFiltrados;

	@Inject
	private ProdutoBo produtoBo;

	private ProdutoFilter filtro;
	private Produto produto;

	public PesquisaProdutosBean() {
		filtro = new ProdutoFilter();
		produto = new Produto();
	}
	
	@PostConstruct
	public void init(){
		pesquisar();
	}

	public void pesquisar() {
		produtosFiltrados = produtoBo.filtrados(filtro);
	}

	public void updateAtivo() {
		try {
			produtoBo.salvar(produto);
			if (produto.getStatus()) {
				FacesUtil.addInfoMessage("Sucesso ao ativar o produto " + produto.getNome());
			} else {
				FacesUtil.addInfoMessage("Sucesso ao desativar o produto " + produto.getNome());
			}
			pesquisar();
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public List<Produto> getProdutosFiltrados() {
		return produtosFiltrados;
	}

	public ProdutoFilter getFiltro() {
		return filtro;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

}
