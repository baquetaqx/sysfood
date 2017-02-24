package com.sysfood.bean;

import java.io.Serializable;
import java.util.Date;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.sysfood.bo.CaixaBo;
import com.sysfood.exception.NegocioException;
import com.sysfood.model.Caixa;
import com.sysfood.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CaixaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Caixa caixa;

	@Inject
	private CaixaBo caixaBo;

	public CaixaBean() {
		caixa = new Caixa();
	}

	public void abrirCaixa() {
		try {
			caixa.setDataDeAbertura(new Date());
			caixa.setCaixaAberto(true);
			caixa = caixaBo.salvar(caixa);

			FacesUtil.addInfoMessage("Caixa aberto com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());

		}
	}

	public void fecharCaixa() {
		caixa = caixaBo.fecharCaixa(caixa);
	}

	public Caixa getCaixa() {
		return caixa;
	}

	public void setCaixa(Caixa caixa) {
		this.caixa = caixa;
	}

}