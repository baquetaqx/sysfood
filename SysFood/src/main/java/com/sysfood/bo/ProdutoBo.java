package com.sysfood.bo;

import java.io.Serializable;

import javax.inject.Inject;

import com.sysfood.dao.ProdutoDao;
import com.sysfood.exception.NegocioException;
import com.sysfood.model.Produto;
import com.sysfood.util.jpa.Transactional;

public class ProdutoBo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ProdutoDao produtoDao;

	@Transactional
	public Produto salvar(Produto produto) throws NegocioException {
		Produto produtoExistente = produtoDao.porNome(produto.getNome());

		if (produtoExistente != null && !produtoExistente.equals(produto)) {
			throw new NegocioException("Já existe um produto com o nome informado.");
		}
		return produtoDao.guardar(produto);
	}

}