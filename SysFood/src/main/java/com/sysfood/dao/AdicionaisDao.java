package com.sysfood.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import com.sysfood.dao.filter.AdicionaisFiltro;
import com.sysfood.dao.filter.ProdutoFilter;
import com.sysfood.model.Adicionais;
import com.sysfood.model.Produto;

public class AdicionaisDao implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	public Adicionais guardar(Adicionais adicionais) {
		return manager.merge(adicionais);
	}
	
	public Adicionais porNome(String nome) {
		try {
			return manager.createQuery("from Adicionais where nome = :nome", Adicionais.class).setParameter("nome", nome)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public List<Adicionais> filtrados(AdicionaisFiltro filtro) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Adicionais> criteriaQuery = builder.createQuery(Adicionais.class);

		Root<Adicionais> adicionais = criteriaQuery.from(Adicionais.class);
		Order order = builder.asc(adicionais.get("nome"));

		criteriaQuery.select(adicionais);

		List<Predicate> predicates = new ArrayList<>();

		if (StringUtils.isNotBlank(filtro.getNome())) {
			predicates.add(builder.like(adicionais.get("nome"), filtro.getNome() + "%"));
		}

		if (filtro.getStatus() != null) {
			predicates.add(builder.equal(adicionais.get("status"), filtro.getStatus()));
		}

		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		criteriaQuery.orderBy(order);

		TypedQuery<Adicionais> query = manager.createQuery(criteriaQuery);
		return query.getResultList();

	}
	
	public Adicionais porId(Long id) {
		return manager.find(Adicionais.class, id);
	}
}
