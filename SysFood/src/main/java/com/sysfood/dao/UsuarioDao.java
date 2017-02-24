package com.sysfood.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import com.sysfood.dao.filter.UsuarioFilter;
import com.sysfood.model.Usuario;

public class UsuarioDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Usuario porId(Long id) {
		return this.manager.find(Usuario.class, id);
	}

	public Usuario guardar(Usuario usuario) {
		return this.manager.merge(usuario);
	}

	public List<Usuario> filtrados(UsuarioFilter filtro) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteriaQuery = builder.createQuery(Usuario.class);

		Root<Usuario> usuario = criteriaQuery.from(Usuario.class);
		Order order = builder.asc(usuario.get("nome"));

		criteriaQuery.select(usuario);

		if (StringUtils.isNotBlank(filtro.getNome())) {
			builder.like(usuario.get("nome"), filtro.getNome() + "%");
		}

		criteriaQuery.orderBy(order);
		TypedQuery<Usuario> query = manager.createQuery(criteriaQuery);
		return query.getResultList();
	}
}
