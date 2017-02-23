package com.sysfood.bo;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.sysfood.dao.UsuarioDao;
import com.sysfood.dao.filter.UsuarioFilter;
import com.sysfood.exception.NegocioException;
import com.sysfood.model.Usuario;
import com.sysfood.util.jpa.Transactional;

public class UsuarioBo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioDao usuarioDao;

	@Transactional
	public Usuario salvar(Usuario usuario) throws NegocioException {
		return usuarioDao.guardar(usuario);
	}

	public List<Usuario> filtrados(UsuarioFilter filtro) {
		return usuarioDao.filtrados(filtro);
	}
	
}
