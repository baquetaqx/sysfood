package com.sysfood.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.sysfood.bo.PedidoBo;
import com.sysfood.dao.filter.PedidoFilter;
import com.sysfood.exception.NegocioException;
import com.sysfood.model.Pedido;
import com.sysfood.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaPedidoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private PedidoFilter pedidoFilterAtivo;
	private PedidoFilter pedidoFilterInativo;
	private List<Pedido> pedidosAtivosFiltrados;
	private List<Pedido> pedidosInativosFiltrados;
	@Inject
	private PedidoBo pedidoBo;

	public PesquisaPedidoBean() {
		pedidoFilterAtivo = new PedidoFilter();
		pedidoFilterInativo = new PedidoFilter();
	}

	public void pesquisarAtivos() {
		pedidoFilterAtivo.setStatus(true);
		pedidosAtivosFiltrados = pedidoBo.filtrados(pedidoFilterAtivo);
	}

	public void pesquisarInativos() {
		pedidoFilterAtivo.setStatus(false);
		pedidosInativosFiltrados = pedidoBo.filtrados(pedidoFilterInativo);
	}

	public void cancelarPedido(Pedido pedido) {
		try {
			pedidoBo.salvar(pedido);
			FacesUtil.addInfoMessage("Pedido Cancelado!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public PedidoFilter getPedidoFilterAtivo() {
		return pedidoFilterAtivo;
	}

	public PedidoFilter getPedidoFilterInativo() {
		return pedidoFilterInativo;
	}

	public List<Pedido> getPedidosAtivosFiltrados() {
		return pedidosAtivosFiltrados;
	}

	public List<Pedido> getPedidosInativosFiltrados() {
		return pedidosInativosFiltrados;
	}

}
