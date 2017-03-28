package com.sysfood.bo;

import java.io.Serializable;

import javax.inject.Inject;

import com.sysfood.dao.PagamentoDao;
import com.sysfood.model.Pagamento;
import com.sysfood.util.jpa.Transactional;

public class PagamentoBo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private PagamentoDao pagamentoDao;

	@Transactional
	public void salvar(Pagamento pagamento) {
		pagamentoDao.guardar(pagamento);
	}

}
