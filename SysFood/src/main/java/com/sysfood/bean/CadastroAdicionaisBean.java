package com.sysfood.bean;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.sysfood.bo.AdicionaisBO;
import com.sysfood.exception.NegocioException;
import com.sysfood.model.Adicionais;
import com.sysfood.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroAdicionaisBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Adicionais adicionais;

	@Inject
	private AdicionaisBO adicionaisBo;

	public CadastroAdicionaisBean() {
		adicionais = new Adicionais();
	}

	public void salvar() {
		try {
			if (this.adicionais.getId()==null) {
				this.adicionais = adicionaisBo.salvar(this.adicionais);
				adicionais = new Adicionais();
				FacesUtil.addInfoMessage("Adicional salvo com sucesso!");	
			}else{
				this.adicionais = adicionaisBo.salvar(this.adicionais);
				adicionais = new Adicionais();
				FacesUtil.addInfoMessage("Adicional editado com sucesso!");
			}
			
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public boolean isEditando() {
		return this.getAdicionais().getId() != null;
	}

	public Adicionais getAdicionais() {
		return adicionais;
	}

	public void setAdicionais(Adicionais adicionais) {
		this.adicionais = adicionais;
	}

}
