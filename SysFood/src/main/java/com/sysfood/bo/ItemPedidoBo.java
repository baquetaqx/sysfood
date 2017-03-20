package com.sysfood.bo;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.sysfood.dao.ItemPedidoDao;
import com.sysfood.model.ItemPedido;
import com.sysfood.model.Pedido;

public class ItemPedidoBo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ItemPedidoDao itemPedidoDao;

	public List<ItemPedido> porPedido(Pedido pedido) {
		return itemPedidoDao.porPedido(pedido);
	}

}
