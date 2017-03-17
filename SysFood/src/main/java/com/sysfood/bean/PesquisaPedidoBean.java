package com.sysfood.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.sysfood.bo.PedidoBo;
import com.sysfood.dao.filter.PedidoFilter;
import com.sysfood.model.Pedido;

@Named
@ViewScoped
public class PesquisaPedidoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private PedidoFilter pedidoFilterAtivo;
	private PedidoFilter pedidoFilterInativo;
	private List<Pedido> PedidosAtivosFiltrados;
	private List<Pedido> PedidosInativosFiltrados;
	@Inject
	private PedidoBo pedidoBo;

	public PesquisaPedidoBean() {
		pedidoFilterAtivo = new PedidoFilter();
		pedidoFilterInativo = new PedidoFilter();
	}

	public void pesquisarAtivos() {
		PedidosAtivosFiltrados = pedidoBo.filtrados(pedidoFilterAtivo);
	}

	public void pesquisarInativos() {
		PedidosInativosFiltrados = pedidoBo.filtrados(pedidoFilterInativo);
	}

	public PedidoFilter getPedidoFilterAtivo() {
		return pedidoFilterAtivo;
	}

	public PedidoFilter getPedidoFilterInativo() {
		return pedidoFilterInativo;
	}

	public List<Pedido> getPedidosAtivosFiltrados() {
		return PedidosAtivosFiltrados;
	}

	public List<Pedido> getPedidosInativosFiltrados() {
		return PedidosInativosFiltrados;
	}

}
