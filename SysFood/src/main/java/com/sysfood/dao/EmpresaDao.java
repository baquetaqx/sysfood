package com.sysfood.dao;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.sysfood.model.Empresa;

public class EmpresaDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Empresa guardar(Empresa empresa) {
		return manager.merge(empresa);
	}

}
