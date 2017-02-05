package com.sysfood.dao;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.sysfood.model.Produto;

public class ProdutoDao implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Produto guardar(Produto produto) {
		return manager.merge(produto);
	}

	public Produto porNome(String nome) {
		try {
			return manager.createQuery("from Produto where nome = :nome", Produto.class)
					.setParameter("nome", nome).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
