package com.sysfood.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.sysfood.model.ItemPedido;
import com.sysfood.model.Pedido;

public class ItemPedidoDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public List<ItemPedido> porPedido(Pedido pedido) {
		try {
			return manager.createQuery("from ItemPedido where pedido = :pedido", ItemPedido.class)
					.setParameter("pedido", pedido).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

}
