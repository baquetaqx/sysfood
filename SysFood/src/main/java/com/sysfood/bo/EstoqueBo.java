package com.sysfood.bo;

import java.io.Serializable;

import javax.inject.Inject;

import com.sysfood.exception.NegocioException;
import com.sysfood.model.ItemPedido;
import com.sysfood.model.Pedido;
import com.sysfood.model.Produto;
import com.sysfood.util.jpa.Transactional;

public class EstoqueBo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	ProdutoBo produtoBo;

	@Transactional
	public void baixarItensEstoque(Pedido pedido) throws NegocioException {

		for (ItemPedido item : pedido.getItens()) {
			Produto produto = item.getProduto().baixarEstoque(item.getQuantidade());
			if (produto != null) {
				produtoBo.salvar(produto);
			}
		}
	}
}
