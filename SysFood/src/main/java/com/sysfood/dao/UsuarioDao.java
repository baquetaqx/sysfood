package com.sysfood.dao;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.sysfood.model.Usuario;

public class UsuarioDao implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;

	public Usuario porId(Long id) {
		return this.manager.find(Usuario.class, id);
	}

	public Usuario guardar(Usuario usuario) {
		return this.manager.merge(usuario);
	}
}
