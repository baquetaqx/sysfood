package com.sysfood.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
	private Caixa caixaDialogo;
	private Date data;

	private List<Caixa> caixaFiltrados;

	@Inject
	private CaixaBo caixaBo;

	public CaixaBean() {
		setCaixa(getCaixa());
	}

	public void abrirCaixa() {
		if (caixa.getId() != null) {
			limpar();
		}
		try {
			caixa.setDataDeAbertura(new Date());
			caixa.setCaixaAberto(true);
			caixa = caixaBo.salvar(caixa);

			FacesUtil.addInfoMessage("Caixa aberto com sucesso!");
		} catch (NegocioException e) {
			limpar();
			FacesUtil.addErrorMessage(e.getMessage());

		}
	}

	private void limpar() {
		caixa = new Caixa();
	}

	public void fecharCaixa() {
		try {
			caixa.setCaixaAberto(false);
			caixa = caixaBo.fecharCaixa(caixa);
			FacesUtil.addInfoMessage("Caixa fechado com sucesso!");
		} catch (NegocioException e) {
			caixa.setCaixaAberto(true);
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public void recuperarCaixa() {
		caixa = caixaBo.recuperarCaixa(new Date());
		setCaixa(getCaixa());
	}

	public void pesquisar() {
		caixaFiltrados = caixaBo.filtrados(data);
	}

	public Caixa getCaixa() {
		return caixa == null ? new Caixa() : caixa;
	}

	public void setCaixa(Caixa caixa) {
		this.caixa = caixa;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public List<Caixa> getCaixaFiltrados() {
		return caixaFiltrados;
	}

	public Caixa getCaixaDialogo() {
		return caixaDialogo;
	}

	public void setCaixaDialogo(Caixa caixaDialogo) {
		this.caixaDialogo = caixaDialogo;
	}

}