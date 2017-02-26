package com.sysfood.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

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
		CriteriaBuilder builder = manager.getCriteriaBuilder();

		CriteriaQuery<BigDecimal> criteriaQuery = builder.createQuery(BigDecimal.class);

		Root<Pedido> pedido = criteriaQuery.from(Pedido.class);
		criteriaQuery.select(builder.sum(pedido.get("valorTotal")));

		ParameterExpression<Date> dataInicial = builder.parameter(Date.class, "dataInicial");
		ParameterExpression<Date> dataFinal = builder.parameter(Date.class, "dataFinal");

		criteriaQuery.where(builder.and(builder.equal(pedido.get("tipoPagamento"), TipoPagamento.CARTAO_DEBITO),
				builder.between(pedido.get("dataPedido"), dataInicial, dataFinal)));

		TypedQuery<BigDecimal> query = manager.createQuery(criteriaQuery);

		Calendar dataIni = Calendar.getInstance();
		dataIni.setTime(caixaDebito.getDataDeAbertura());
		Calendar dataFin = Calendar.getInstance();
		dataFin.setTime(caixaDebito.getDataDeAbertura());
		dataFin.set(Calendar.DATE, dataFin.get(Calendar.DATE) + 1);

		query.setParameter("dataInicial", dataIni.getTime());
		query.setParameter("dataFinal", dataFin.getTime());

		return query.getSingleResult() == null ? BigDecimal.ZERO : query.getSingleResult();
	}

	public BigDecimal calcularCredito(Caixa caixaCredito) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();

		CriteriaQuery<BigDecimal> criteriaQuery = builder.createQuery(BigDecimal.class);

		Root<Pedido> pedido = criteriaQuery.from(Pedido.class);
		criteriaQuery.select(builder.sum(pedido.get("valorTotal")));

		ParameterExpression<Date> dataInicial = builder.parameter(Date.class, "dataInicial");
		ParameterExpression<Date> dataFinal = builder.parameter(Date.class, "dataFinal");

		criteriaQuery.where(builder.and(builder.equal(pedido.get("tipoPagamento"), TipoPagamento.CARTAO_CREDITO),
				builder.between(pedido.get("dataPedido"), dataInicial, dataFinal)));

		TypedQuery<BigDecimal> query = manager.createQuery(criteriaQuery);

		Calendar dataIni = Calendar.getInstance();
		dataIni.setTime(caixaCredito.getDataDeAbertura());
		Calendar dataFin = Calendar.getInstance();
		dataFin.setTime(caixaCredito.getDataDeAbertura());
		dataFin.set(Calendar.DATE, dataFin.get(Calendar.DATE) + 1);

		query.setParameter("dataInicial", dataIni.getTime());
		query.setParameter("dataFinal", dataFin.getTime());

		return query.getSingleResult() == null ? BigDecimal.ZERO : query.getSingleResult();
	}

	public BigDecimal calcularDinheiro(Caixa caixaDinheiro) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();

		CriteriaQuery<BigDecimal> criteriaQuery = builder.createQuery(BigDecimal.class);

		Root<Pedido> pedido = criteriaQuery.from(Pedido.class);
		criteriaQuery.select(builder.sum(pedido.get("valorTotal")));

		ParameterExpression<Date> dataInicial = builder.parameter(Date.class, "dataInicial");
		ParameterExpression<Date> dataFinal = builder.parameter(Date.class, "dataFinal");

		criteriaQuery.where(builder.and(builder.equal(pedido.get("tipoPagamento"), TipoPagamento.DINHEIRO),
				builder.between(pedido.get("dataPedido"), dataInicial, dataFinal)));

		TypedQuery<BigDecimal> query = manager.createQuery(criteriaQuery);

		Calendar dataIni = Calendar.getInstance();
		dataIni.setTime(caixaDinheiro.getDataDeAbertura());
		Calendar dataFin = Calendar.getInstance();
		dataFin.setTime(caixaDinheiro.getDataDeAbertura());
		dataFin.set(Calendar.DATE, dataFin.get(Calendar.DATE) + 1);

		query.setParameter("dataInicial", dataIni.getTime());
		query.setParameter("dataFinal", dataFin.getTime());

		return query.getSingleResult() == null ? BigDecimal.ZERO : query.getSingleResult();
	}

	// SELECT COUNTnome_coluna) FROM nome_tabela
	public Long calcularQuantidadePedidos(Caixa caixaQuantidade) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();

		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);

		Root<Pedido> pedido = criteriaQuery.from(Pedido.class);
		criteriaQuery.multiselect(builder.count(pedido));

		criteriaQuery.where(builder.equal(pedido.get("dataPedido"), caixaQuantidade.getDataDeAbertura()));

		TypedQuery<Long> query = manager.createQuery(criteriaQuery);
		return query.getSingleResult();
	}

	public Caixa porId(Long id) {
		return manager.find(Caixa.class, id);
	}

}
