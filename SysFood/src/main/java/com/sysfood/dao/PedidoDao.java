package com.sysfood.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.sysfood.dao.filter.PedidoFilter;
import com.sysfood.model.ItemPedido;
import com.sysfood.model.Pedido;
import com.sysfood.model.Produto;
import com.sysfood.model.SecaoProduto;

public class PedidoDao {

	@Inject
	private EntityManager manager;

	public Pedido porId(Long id) {
		return this.manager.find(Pedido.class, id);
	}

	public Pedido guardar(Pedido pedido) {
		return this.manager.merge(pedido);
	}

	public List<Pedido> filtrados(PedidoFilter filtro) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = builder.createQuery(Pedido.class);

		Root<Pedido> pedido = criteriaQuery.from(Pedido.class);

		criteriaQuery.select(pedido);

		List<Predicate> predicates = new ArrayList<>();

		if (StringUtils.isNotBlank(filtro.getCliente())) {
			predicates.add(builder.like(pedido.get("cliente"), "%" + filtro.getCliente() + "%"));
		}
		if (filtro.getStatus() != null) {
			predicates.add(builder.equal(pedido.get("status"), filtro.getStatus()));
		}
		if (filtro.getDataPedido() != null) {
			ParameterExpression<Date> dataInicial = builder.parameter(Date.class, "dataInicial");
			ParameterExpression<Date> dataFinal = builder.parameter(Date.class, "dataFinal");
			predicates.add(builder.between(pedido.get("dataPedido"), dataInicial, dataFinal));
		}

		criteriaQuery.where(predicates.toArray(new Predicate[0]));

		TypedQuery<Pedido> query = manager.createQuery(criteriaQuery);

		if (filtro.getDataPedido() != null) {
			Calendar dataIni = Calendar.getInstance();
			dataIni.setTime(filtro.getDataPedido());
			dataIni.set(Calendar.HOUR_OF_DAY, 0);
			dataIni.set(Calendar.MINUTE, 0);
			dataIni.set(Calendar.SECOND, 0);

			Calendar dataFin = Calendar.getInstance();
			dataFin.setTime(filtro.getDataPedido());
			dataFin.set(Calendar.HOUR_OF_DAY, 23);
			dataFin.set(Calendar.MINUTE, 59);
			dataFin.set(Calendar.SECOND, 59);

			query.setParameter("dataInicial", dataIni.getTime());
			query.setParameter("dataFinal", dataFin.getTime());
		}

		return query.getResultList();
	}

	/*
	 * select sum(i.quantidade) as quantidade , p.data_pedido as data from
	 * item_pedido i inner join pedido p on i.pedido_id = p.id inner join
	 * produto pro on i.produto_id = pro.id where p.data_pedido > '2017-02-25'
	 * and p.status = 'T' and pro.secao_produto = 'REFRIGERANTES' group by
	 * p.data_pedido
	 */
	public Map<Date, Integer> quantidadeDeProdutosPorData(Integer numeroDeDias, SecaoProduto secaoProduto) {
		numeroDeDias -= 1;
		Calendar dataInicial = Calendar.getInstance();
		dataInicial = DateUtils.truncate(dataInicial, Calendar.DAY_OF_MONTH);
		dataInicial.add(Calendar.DAY_OF_MONTH, numeroDeDias * -1);

		Map<Date, Integer> resultado = criarMapaVazio(numeroDeDias, dataInicial);

		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);

		Root<ItemPedido> itemPedido = criteriaQuery.from(ItemPedido.class);
		Join<ItemPedido, Pedido> pedido = (Join) itemPedido.join("pedido");
		Join<ItemPedido, Produto> produto = (Join) itemPedido.join("produto");

		criteriaQuery.multiselect(builder.sum(itemPedido.get("quantidade")), pedido.get("dataPedido"));

		criteriaQuery.where(builder.and(builder.between(pedido.get("dataPedido"), dataInicial.getTime(), new Date())),
				builder.equal(pedido.get("status"), true), builder.equal(produto.get("secao"), secaoProduto));

		criteriaQuery.groupBy(pedido.get("dataPedido"));

		TypedQuery<Object[]> query = manager.createQuery(criteriaQuery);
		List<Object[]> res = query.getResultList();

		for (Object[] valores : res) {
			resultado.put((Date) valores[1], Integer.valueOf(valores[0].toString()));
		}

		return resultado;
	}

	private Map<Date, Integer> criarMapaVazio(Integer numeroDeDias, Calendar dataInicial) {
		dataInicial = (Calendar) dataInicial.clone();
		Map<Date, Integer> mapaInicial = new TreeMap<>();

		for (int i = 0; i <= numeroDeDias; i++) {
			mapaInicial.put(dataInicial.getTime(), 0);
			dataInicial.add(Calendar.DAY_OF_MONTH, 1);
		}

		return mapaInicial;
	}

}
