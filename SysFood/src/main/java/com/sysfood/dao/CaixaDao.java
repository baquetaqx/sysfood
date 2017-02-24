package com.sysfood.dao;

import java.math.BigDecimal;
import java.util.Date;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.sysfood.model.Caixa;
import com.sysfood.model.Pedido;

public class CaixaDao {

	@Inject
	private EntityManager manager;

	public Caixa porData(Date dataDeAbertura) {
		return manager.createQuery("from Caixa where dataDeAbertura = :dataDeAbertura", Caixa.class)
				.setParameter("dataDeAbertura", dataDeAbertura).getSingleResult();
	}

	public Caixa guardar(Caixa caixa) {
		return manager.merge(caixa);
	}

	public BigDecimal calcularDebito(Caixa caixaDebito) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();

		CriteriaQuery<BigDecimal> criteriaQuery = builder.createQuery(BigDecimal.class);

		Root<Pedido> pedido = criteriaQuery.from(Pedido.class);
		criteriaQuery.select(builder.sum(pedido.get("valorTotal")));

		criteriaQuery.where(builder.and(builder.equal(pedido.get("tipoPagamento"), "DEBITO"),
				builder.equal(pedido.get("dataPedido"), caixaDebito.getDataDeAbertura())));

		TypedQuery<BigDecimal> query = manager.createQuery(criteriaQuery);
		return query.getSingleResult();
	}

	public BigDecimal calcularCredito(Caixa caixaCredito) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();

		CriteriaQuery<BigDecimal> criteriaQuery = builder.createQuery(BigDecimal.class);

		Root<Pedido> pedido = criteriaQuery.from(Pedido.class);
		criteriaQuery.select(builder.sum(pedido.get("valorTotal")));

		criteriaQuery.where(builder.and(builder.equal(pedido.get("tipoPagamento"), "CREDITO"),
				builder.equal(pedido.get("dataPedido"), caixaCredito.getDataDeAbertura())));

		TypedQuery<BigDecimal> query = manager.createQuery(criteriaQuery);
		return query.getSingleResult();
	}

	public BigDecimal calcularDinheiro(Caixa caixaDinheiro) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();

		CriteriaQuery<BigDecimal> criteriaQuery = builder.createQuery(BigDecimal.class);

		Root<Pedido> pedido = criteriaQuery.from(Pedido.class);
		criteriaQuery.select(builder.sum(pedido.get("valorTotal")));

		criteriaQuery.where(builder.and(builder.equal(pedido.get("tipoPagamento"), "DINHEIRO"),
				builder.equal(pedido.get("dataPedido"), caixaDinheiro.getDataDeAbertura())));

		TypedQuery<BigDecimal> query = manager.createQuery(criteriaQuery);
		return query.getSingleResult();
	}

	// SELECT COUNTnome_coluna) FROM nome_tabela
	public Integer calcularQuantidadePedidos(Caixa caixaQuantidade) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();

		CriteriaQuery<Integer> criteriaQuery = builder.createQuery(Integer.class);

		Root<Pedido> pedido = criteriaQuery.from(Pedido.class);
		criteriaQuery.multiselect(builder.count(pedido));

		criteriaQuery.where(builder.equal(pedido.get("dataPedido"), caixaQuantidade.getDataDeAbertura()));

		TypedQuery<Integer> query = manager.createQuery(criteriaQuery);
		return query.getSingleResult();
	}

}
