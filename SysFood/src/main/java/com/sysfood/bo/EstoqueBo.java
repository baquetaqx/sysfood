package com.sysfood.bo;

import java.io.Serializable;

import javax.inject.Inject;

import com.sysfood.dao.PedidoDao;
import com.sysfood.exception.NegocioException;
import com.sysfood.model.ItemPedido;
import com.sysfood.model.Pedido;
import com.sysfood.util.jpa.Transactional;

public class EstoqueBo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private PedidoDao pedidoDao;

	@Transactional
	public void baixarItensEstoque(Pedido pedido) throws NegocioException {
		pedido = this.pedidoDao.porId(pedido.getId());

		for (ItemPedido item : pedido.getItens()) {
			item.getProduto().baixarEstoque(item.getQuantidade());
		}
	}

}
