package com.sysfood.bo;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.sysfood.dao.UsuarioDao;
import com.sysfood.dao.filter.UsuarioFilter;
import com.sysfood.exception.NegocioException;
import com.sysfood.model.Usuario;
import com.sysfood.util.jpa.Transactional;

public class UsuarioBo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioDao usuarioDao;

	@Transactional
	public Usuario salvar(Usuario usuario) throws NegocioException {
		usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
		return usuarioDao.guardar(usuario);
	}

	public List<Usuario> filtrados(UsuarioFilter filtro) {
		return usuarioDao.filtrados(filtro);
	}

	@Transactional
	public Usuario alterarSenha(String cpf, String senhaAtual, String novaSenha) throws NegocioException {
		Usuario usuario = usuarioDao.porCPF(cpf);
		if (new BCryptPasswordEncoder().matches(senhaAtual, usuario.getSenha())) {
			usuario.setSenha(new BCryptPasswordEncoder().encode(novaSenha));
			return usuarioDao.guardar(usuario);
		}
		throw new NegocioException("Senha atual incorreta, tente novamente!");
	}

}