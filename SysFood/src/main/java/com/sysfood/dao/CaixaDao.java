package com.sysfood.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import com.sysfood.model.Caixa;
import com.sysfood.model.Pedido;
import com.sysfood.model.Produto;
import com.sysfood.model.TipoPagamento;

public class CaixaDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Caixa porData(Date dataDeAbertura) {
		try {
			return manager.createQuery("from Caixa where dataDeAbertura = :dataDeAbertura", Caixa.class)
					.setParameter("dataDeAbertura", dataDeAbertura).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Caixa guardar(Caixa caixa) {
		return manager.merge(caixa);
	}

	public BigDecimal calcularDebito(Caixa caixaDebito) {
		return calcular(caixaDebito, TipoPagamento.CARTAO_DEBITO);
	}

	public BigDecimal calcularCredito(Caixa caixaCredito) {
		return calcular(caixaCredito, TipoPagamento.CARTAO_CREDITO);
	}

	public BigDecimal calcularDinheiro(Caixa caixaDinheiro) {
		return calcular(caixaDinheiro, TipoPagamento.DINHEIRO);
	}

	// SELECT COUNTnome_coluna) FROM nome_tabela
	public Long calcularQuantidadePedidos(Caixa caixaQuantidade) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();

		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);

		Root<Pedido> pedido = criteriaQuery.from(Pedido.class);
		criteriaQuery.multiselect(builder.count(pedido));

		ParameterExpression<Date> dataInicial = builder.parameter(Date.class, "dataInicial");
		ParameterExpression<Date> dataFinal = builder.parameter(Date.class, "dataFinal");

		criteriaQuery.where(builder.and(builder.between(pedido.get("dataPedido"), dataInicial, dataFinal),
				builder.equal(pedido.get("status"), true)));

		TypedQuery<Long> query = manager.createQuery(criteriaQuery);

		Calendar dataIni = Calendar.getInstance();
		dataIni.setTime(caixaQuantidade.getDataDeAbertura());
		Calendar dataFin = Calendar.getInstance();
		dataFin.setTime(new Date());

		query.setParameter("dataInicial", dataIni.getTime());
		query.setParameter("dataFinal", dataFin.getTime());

		return query.getSingleResult();
	}

	public Caixa porId(Long id) {
		return manager.find(Caixa.class, id);
	}

	private BigDecimal calcular(Caixa caixa, TipoPagamento tipoPagamento) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();

		CriteriaQuery<BigDecimal> criteriaQuery = builder.createQuery(BigDecimal.class);

		Root<Pedido> pedido = criteriaQuery.from(Pedido.class);
		criteriaQuery.select(builder.sum(pedido.get("valorTotal")));

		ParameterExpression<Date> dataInicial = builder.parameter(Date.class, "dataInicial");
		ParameterExpression<Date> dataFinal = builder.parameter(Date.class, "dataFinal");

		criteriaQuery.where(builder.and(builder.equal(pedido.get("tipoPagamento"), tipoPagamento),
				builder.between(pedido.get("dataPedido"), dataInicial, dataFinal),
				builder.equal(pedido.get("status"), true)));

		TypedQuery<BigDecimal> query = manager.createQuery(criteriaQuery);

		Calendar dataIni = Calendar.getInstance();
		dataIni.setTime(caixa.getDataDeAbertura());
		Calendar dataFin = Calendar.getInstance();
		dataFin.setTime(new Date());

		query.setParameter("dataInicial", dataIni.getTime());
		query.setParameter("dataFinal", dataFin.getTime());

		return query.getSingleResult() == null ? BigDecimal.ZERO : query.getSingleResult();
	}

	public List<Caixa> filtrados(Date data) {
		if (data != null) {
			return manager.createQuery("from Caixa where dataDeAbertura = :dataDeAbertura", Caixa.class)
					.setParameter("dataDeAbertura", data).getResultList();
		}
		return manager.createQuery("from Caixa", Caixa.class).getResultList();
	}

	public Produto porNome(String nome) {
		try {
			return manager.createQuery("from Produto where nome = :nome", Produto.class).setParameter("nome", nome)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
