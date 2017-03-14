package com.sysfood.bean;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.sysfood.bo.AdicionalBO;
import com.sysfood.exception.NegocioException;
import com.sysfood.model.Adicional;
import com.sysfood.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroAdicionalBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Adicional adicional;

	@Inject
	private AdicionalBO adicionalBo;

	public CadastroAdicionalBean() {
		adicional = new Adicional();
	}

	public void salvar() {
		try {
			if (this.adicional.getId()==null) {
				this.adicional = adicionalBo.salvar(this.adicional);
				adicional = new Adicional();
				FacesUtil.addInfoMessage("Adicional salvo com sucesso!");	
			}else{
				this.adicional = adicionalBo.salvar(this.adicional);
				adicional = new Adicional();
				FacesUtil.addInfoMessage("Adicional editado com sucesso!");
			}
			
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public boolean isEditando() {
		return this.getAdicional().getId() != null;
	}

	public Adicional getAdicional() {
		return adicional;
	}

	public void setAdicional(Adicional adicional) {
		this.adicional = adicional;
	}

}
