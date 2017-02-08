package com.sysfood.bean;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.sysfood.bo.EmpresaBo;
import com.sysfood.model.Empresa;
import com.sysfood.util.jsf.FacesUtil;

@Named
@ApplicationScoped
public class EmpresaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Empresa empresa;

	@Inject
	private EmpresaBo empresaBo;

	public EmpresaBean() {
		setEmpresa(new Empresa());
	}

	public void salvar() {
		empresa = empresaBo.salvar(empresa);

		FacesUtil.addInfoMessage("Empresa salva com sucesso!");
	}

	public boolean isEditando() {
		return empresa.getId() != null;
	}

	public Empresa getEmpresa() {
		return empresa == null ? new Empresa() : empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

}