package com.sysfood.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.sysfood.bo.AdicionalBO;
import com.sysfood.exception.NegocioException;
import com.sysfood.model.Adicional;
import com.sysfood.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaAdicionalBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Adicional> adicionalFiltrados;

	@Inject
	private AdicionalBO adicionalBo;

	private Adicional filtro;
	private Adicional adicional;

	public PesquisaAdicionalBean() {
		filtro = new Adicional();
		adicional = new Adicional();
	}

	@PostConstruct
	public void init() {
		pesquisar();
	}

	public void pesquisar() {
		adicionalFiltrados = adicionalBo.filtrados(filtro);
	}

	public void updateAtivo() {
		try {
			adicionalBo.salvar(adicional);
			if (adicional.getStatus()) {
				FacesUtil.addInfoMessage("Sucesso ao ativar o produto " + adicional.getNome());
			} else {
				FacesUtil.addInfoMessage("Sucesso ao desativar o produto " + adicional.getNome());
			}
			pesquisar();
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public Adicional getAdicional() {
		return adicional;
	}

	public void setAdicional(Adicional adicional) {
		this.adicional = adicional;
	}

	public List<Adicional> getAdicionalFiltrados() {
		return adicionalFiltrados;
	}

	public void setUsuariosFiltrados(List<Adicional> adicionalFiltrados) {
		this.adicionalFiltrados = adicionalFiltrados;
	}

	public Adicional getFiltro() {
		return filtro;
	}

	public void setAdicionalFiltrados(List<Adicional> adicionalFiltrados) {
		this.adicionalFiltrados = adicionalFiltrados;
	}

	public void setFiltro(Adicional filtro) {
		this.filtro = filtro;
	}

}
