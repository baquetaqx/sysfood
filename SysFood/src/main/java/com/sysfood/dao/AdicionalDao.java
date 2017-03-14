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

import com.sysfood.model.Adicional;

public class AdicionalDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Adicional guardar(Adicional adicional) {
		return manager.merge(adicional);
	}

	public Adicional porNome(String nome) {
		try {
			return manager.createQuery("from Adicional where nome = :nome", Adicional.class).setParameter("nome", nome)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Adicional> filtrados(Adicional filtro) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Adicional> criteriaQuery = builder.createQuery(Adicional.class);

		Root<Adicional> adicional = criteriaQuery.from(Adicional.class);
		Order order = builder.asc(adicional.get("nome"));

		criteriaQuery.select(adicional);

		List<Predicate> predicates = new ArrayList<>();

		if (StringUtils.isNotBlank(filtro.getNome())) {
			predicates.add(builder.like(adicional.get("nome"), "%" + filtro.getNome() + "%"));
		}

		if (filtro.getStatus() != null) {
			predicates.add(builder.equal(adicional.get("status"), filtro.getStatus()));
		}

		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		criteriaQuery.orderBy(order);

		TypedQuery<Adicional> query = manager.createQuery(criteriaQuery);
		return query.getResultList();

	}

	public Adicional porId(Long id) {
		return manager.find(Adicional.class, id);
	}
}
