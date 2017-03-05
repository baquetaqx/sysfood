package com.sysfood.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.sysfood.bo.UsuarioBo;
import com.sysfood.dao.filter.UsuarioFilter;
import com.sysfood.model.Usuario;

@Named
@ViewScoped
public class PesquisaUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Usuario> usuariosFiltrados;

	@Inject
	private UsuarioBo usuarioBo;

	private UsuarioFilter filtro;
	private Usuario usuario;

	public PesquisaUsuarioBean() {
		filtro = new UsuarioFilter();
		usuario = new Usuario();
	}
	
	@PostConstruct
	public void init(){
		pesquisar();
	}

	public void pesquisar() {
		usuariosFiltrados = usuarioBo.filtrados(filtro);
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuariosFiltrados() {
		return usuariosFiltrados;
	}

	public void setUsuariosFiltrados(List<Usuario> usuariosFiltrados) {
		this.usuariosFiltrados = usuariosFiltrados;
	}

	public UsuarioFilter getFiltro() {
		return filtro;
	}

}
