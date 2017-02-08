package com.sysfood.bo;

import java.io.Serializable;

import javax.inject.Inject;

import com.sysfood.dao.EmpresaDao;
import com.sysfood.model.Empresa;
import com.sysfood.util.jpa.Transactional;

public class EmpresaBo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EmpresaDao empresaDao;

	@Transactional
	public Empresa salvar(Empresa empresa) {
		return empresaDao.guardar(empresa);
	}

}
