package com.sysfood.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.sysfood.model.Pedido;

public class PedidoDao {

	@Inject
	private EntityManager manager;

	public Pedido porId(Long id) {
		return this.manager.find(Pedido.class, id);
	}

	public Pedido guardar(Pedido pedido) {
		return this.manager.merge(pedido);
	}

}
