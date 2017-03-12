package com.sysfood.bo;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.sysfood.dao.AdicionaisDao;
import com.sysfood.dao.filter.AdicionaisFiltro;
import com.sysfood.exception.NegocioException;
import com.sysfood.model.Adicionais;
import com.sysfood.util.jpa.Transactional;

public class AdicionaisBO implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private AdicionaisDao adicionalDao;

	@Transactional
	public Adicionais salvar(Adicionais adicionais) throws NegocioException {
		Adicionais adicionalExistente = adicionalDao.porNome(adicionais.getNome());

		if (adicionalExistente != null && !adicionalExistente.equals(adicionais)) {
			throw new NegocioException("Já existe um adicional com o nome informado.");
		}
		return adicionalDao.guardar(adicionais);
	}

	public List<Adicionais> filtrados(AdicionaisFiltro filtro) {
		return adicionalDao.filtrados(filtro);
	}

}
