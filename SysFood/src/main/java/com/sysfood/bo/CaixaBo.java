package com.sysfood.bo;

import java.math.BigDecimal;
import java.util.Date;

import javax.inject.Inject;

import com.sysfood.dao.CaixaDao;
import com.sysfood.exception.NegocioException;
import com.sysfood.model.Caixa;
import com.sysfood.util.jpa.Transactional;

public class CaixaBo {

	@Inject
	private CaixaDao caixaDao;

	@Transactional
	public Caixa salvar(Caixa caixa) throws NegocioException {
		Caixa caixaExistente = caixaDao.porData(caixa.getDataDeAbertura());

		if (caixaExistente != null && caixaExistente.equals(caixa)) {
			throw new NegocioException("Caixa já foi aberto hoje!");
		}
		return caixaDao.guardar(caixa);
	}

	public Caixa fecharCaixa(Caixa caixa) {
		BigDecimal debito = caixaDao.calcularDebito(caixa);
		BigDecimal credito = caixaDao.calcularCredito(caixa);
		BigDecimal dinheiro = caixaDao.calcularDinheiro(caixa);
		Integer quantidadePedidos = caixaDao.calcularQuantidadePedidos(caixa);
		BigDecimal total = BigDecimal.ZERO;
		total.add(debito).add(credito).add(dinheiro);

		caixa.setDebito(debito);
		caixa.setDebito(debito);
		caixa.setDinheiro(dinheiro);
		caixa.setNumeroDePedidos(quantidadePedidos);
		caixa.setTotal(total);
		caixa.setCaixaAberto(false);

		return caixaDao.guardar(caixa);

	}

	public Caixa recuperarCaixa(Date date) {
		return caixaDao.porData(date);
	}

}
