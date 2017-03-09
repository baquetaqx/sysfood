package com.sysfood.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.sysfood.bo.AdicionaisBO;
import com.sysfood.dao.filter.AdicionaisFiltro;
import com.sysfood.exception.NegocioException;
import com.sysfood.model.Adicionais;
import com.sysfood.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaAdicionaisBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Adicionais> adicionaisFiltrados;

	@Inject
	private AdicionaisBO adicionaisBo;

	private AdicionaisFiltro filtro;
	private Adicionais adicionais;

	public PesquisaAdicionaisBean() {
		filtro = new AdicionaisFiltro();
		adicionais = new Adicionais();
	}

	@PostConstruct
	public void init() {
		pesquisar();
	}

	public void pesquisar() {
		adicionaisFiltrados = adicionaisBo.filtrados(filtro);
	}
	
	public void updateAtivo() {
		try {
			adicionaisBo.salvar(adicionais);
			if (adicionais.getStatus()) {
				FacesUtil.addInfoMessage("Sucesso ao ativar o produto " + adicionais.getNome());
			} else {
				FacesUtil.addInfoMessage("Sucesso ao desativar o produto " + adicionais.getNome());
			}
			pesquisar();
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public Adicionais getAdicionais() {
		return adicionais;
	}

	public void setAdicionais(Adicionais adicionais) {
		this.adicionais = adicionais;
	}

	public List<Adicionais> getAdicionaisFiltrados() {
		return adicionaisFiltrados;
	}

	public void setUsuariosFiltrados(List<Adicionais> adicionaisFiltrados) {
		this.adicionaisFiltrados = adicionaisFiltrados;
	}

	public AdicionaisFiltro getFiltro() {
		return filtro;
	}

	public void setAdicionaisFiltrados(List<Adicionais> adicionaisFiltrados) {
		this.adicionaisFiltrados = adicionaisFiltrados;
	}

	public void setFiltro(AdicionaisFiltro filtro) {
		this.filtro = filtro;
	}

}
