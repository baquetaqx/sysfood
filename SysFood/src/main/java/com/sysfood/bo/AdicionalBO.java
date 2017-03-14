package com.sysfood.bo;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.sysfood.dao.AdicionalDao;
import com.sysfood.exception.NegocioException;
import com.sysfood.model.Adicional;
import com.sysfood.util.jpa.Transactional;

public class AdicionalBO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private AdicionalDao adicionalDao;

	@Transactional
	public Adicional salvar(Adicional adicional) throws NegocioException {
		Adicional adicionalExistente = adicionalDao.porNome(adicional.getNome());

		if (adicionalExistente != null && !adicionalExistente.equals(adicional)) {
			throw new NegocioException("Já existe um adicional com o nome informado.");
		}
		return adicionalDao.guardar(adicional);
	}

	public List<Adicional> filtrados(Adicional filtro) {
		return adicionalDao.filtrados(filtro);
	}

}
