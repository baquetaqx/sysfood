package com.sysfood.bean;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.sysfood.bo.UsuarioBo;
import com.sysfood.exception.NegocioException;
import com.sysfood.model.PerfilUsuario;
import com.sysfood.model.Usuario;
import com.sysfood.util.jsf.FacesUtil;

@Named
@ViewScoped
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Usuario usuario;
	private String senhaAtual;
	private String novaSenha;

	@Inject
	private UsuarioBo usuarioBo;

	public UsuarioBean() {
		limpar();
	}

	public void salvar() {
		try {
			this.usuario = usuarioBo.salvar(usuario);
			limpar();
			FacesUtil.addInfoMessage("Usuário salvo com sucesso");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public void alterarSenha() {
		try {
			usuario = usuarioBo.alterarSenha(usuario.getCpf(), senhaAtual, novaSenha);
			senhaAtual = null;
			FacesUtil.addInfoMessage("Senha alterada com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	private void limpar() {
		usuario = new Usuario();
	}

	public PerfilUsuario[] getPerfil() {
		return PerfilUsuario.values();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

}
