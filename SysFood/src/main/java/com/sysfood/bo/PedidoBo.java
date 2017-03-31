package com.sysfood.bo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import com.sysfood.dao.PedidoDao;
import com.sysfood.dao.filter.PedidoFilter;
import com.sysfood.exception.NegocioException;
import com.sysfood.model.Pedido;
import com.sysfood.util.jpa.Transactional;
import com.sysfood.util.jsf.FacesUtil;

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
		if (pedido.getStatus()) {
			pedido.setDataPedido(new Date());

			if (pedido.getItens().isEmpty()) {
				throw new NegocioException("O pedido deve possuir pelo menos um item.");
			}

			estoqueBo.baixarItensEstoque(pedido);
			Pedido pedidoImprimir = pedido;
			pedido = pedidoDao.guardar(pedido);
			try {
				cumpomBo.imprimirCupom(pedidoImprimir);
			} catch (NegocioException e) {
				FacesUtil.addErrorMessage(e.getMessage());
			}
			return pedido;
		}
		estoqueBo.remontarItensEstoque(pedido);
		return pedidoDao.guardar(pedido);

	}

	public List<Pedido> filtrados(PedidoFilter pedidoFilterAtivo) {
		return pedidoDao.filtrados(pedidoFilterAtivo);
	}

}
