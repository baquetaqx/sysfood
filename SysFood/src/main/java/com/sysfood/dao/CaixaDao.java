package com.sysfood.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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

		criteriaQuery.where(builder.and(builder.equal(pedido.get("tipoPagamento"), TipoPagamento.CARTAO_DEBITO),
				builder.equal(pedido.get("dataPedido"), caixaDebito.getDataDeAbertura())));

		TypedQuery<BigDecimal> query = manager.createQuery(criteriaQuery);

		return query.getSingleResult() == null ? BigDecimal.ZERO : query.getSingleResult();
	}

	public BigDecimal calcularCredito(Caixa caixaCredito) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();

		CriteriaQuery<BigDecimal> criteriaQuery = builder.createQuery(BigDecimal.class);

		Root<Pedido> pedido = criteriaQuery.from(Pedido.class);
		criteriaQuery.select(builder.sum(pedido.get("valorTotal")));

		criteriaQuery.where(builder.and(builder.equal(pedido.get("tipoPagamento"), TipoPagamento.CARTAO_CREDITO),
				builder.equal(pedido.get("dataPedido"), caixaCredito.getDataDeAbertura())));

		TypedQuery<BigDecimal> query = manager.createQuery(criteriaQuery);
		return query.getSingleResult() == null ? BigDecimal.ZERO : query.getSingleResult();
	}

	public BigDecimal calcularDinheiro(Caixa caixaDinheiro) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();

		CriteriaQuery<BigDecimal> criteriaQuery = builder.createQuery(BigDecimal.class);

		Root<Pedido> pedido = criteriaQuery.from(Pedido.class);
		criteriaQuery.select(builder.sum(pedido.get("valorTotal")));

		criteriaQuery.where(builder.and(builder.equal(pedido.get("tipoPagamento"), TipoPagamento.DINHEIRO),
				builder.equal(pedido.get("dataPedido"), caixaDinheiro.getDataDeAbertura())));

		TypedQuery<BigDecimal> query = manager.createQuery(criteriaQuery);
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

}
