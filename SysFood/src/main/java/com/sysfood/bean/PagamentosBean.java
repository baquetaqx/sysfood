package com.sysfood.bean;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.sysfood.bo.PagamentoBo;
import com.sysfood.model.Pagamento;
import com.sysfood.model.TipoDespesa;
import com.sysfood.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PagamentosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Pagamento pagamento;
	@Inject
	PagamentoBo pagamentoBo;

	public PagamentosBean() {
		limpar();
	}

	private void limpar() {
		pagamento = new Pagamento();
	}

	public void salvar() {
		pagamentoBo.salvar(pagamento);
		limpar();
		FacesUtil.addInfoMessage("Pagamento salvo com sucesso!");
	}

	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}

	public TipoDespesa[] getTipoDespesa() {
		return TipoDespesa.values();
	}

}
