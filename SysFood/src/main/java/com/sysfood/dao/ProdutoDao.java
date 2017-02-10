package com.sysfood.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import com.sysfood.dao.filter.ProdutoFilter;
import com.sysfood.model.Produto;

public class ProdutoDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Produto guardar(Produto produto) {
		return manager.merge(produto);
	}

	public Produto porNome(String nome) {
		try {
			return manager.createQuery("from Produto where nome = :nome", Produto.class).setParameter("nome", nome)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Produto> filtrados(ProdutoFilter filtro) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = builder.createQuery(Produto.class);

		Root<Produto> produto = criteriaQuery.from(Produto.class);
		Order order = builder.asc(produto.get("nome"));

		criteriaQuery.select(produto);

		if (StringUtils.isNotBlank(filtro.getNome())) {
			criteriaQuery.where(builder.like(produto.get("nome"), filtro.getNome() + "%"));
		}

		criteriaQuery.orderBy(order);

		TypedQuery<Produto> query = manager.createQuery(criteriaQuery);
		return query.getResultList();

	}

	public Produto porId(Long id) {
		return manager.find(Produto.class, id);
	}

}
