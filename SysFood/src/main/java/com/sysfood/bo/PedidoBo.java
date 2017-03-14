package com.sysfood.bo;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.sysfood.dao.PedidoDao;
import com.sysfood.exception.NegocioException;
import com.sysfood.model.Pedido;
import com.sysfood.util.jpa.Transactional;

public class PedidoBo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private PedidoDao pedidoDao;

	@Inject
	private EstoqueBo estoqueBo;
	
	@Inject
	private CumpomBo cumpomBo;

	@Transactional
	public Pedido salvar(Pedido pedido) throws NegocioException {
		pedido.setDataPedido(new Date());

		if (pedido.getItens().isEmpty()) {
			throw new NegocioException("O pedido deve possuir pelo menos um item.");
		}

		estoqueBo.baixarItensEstoque(pedido);

		pedido = pedidoDao.guardar(pedido);
		
//		cumpomBo.imprimirCupom(pedido);
		
		return pedido;
	}

}
