package com.sysfood.dao;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.sysfood.model.Pagamento;

public class PagamentoDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public void guardar(Pagamento pagamento) {
		manager.merge(pagamento);
	}

}
