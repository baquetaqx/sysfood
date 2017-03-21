package com.sysfood.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.sysfood.bo.ItemPedidoBo;
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
	private String motivo;
	private Pedido pedido;
	private Pedido peditoDialogo;
	@Inject
	private PedidoBo pedidoBo;
	@Inject
	private ItemPedidoBo itemPedidoBo;

	public PesquisaPedidoBean() {
		pedidoFilterAtivo = new PedidoFilter();
		pedidoFilterInativo = new PedidoFilter();
		pedido = new Pedido();
	}

	public void pesquisarAtivos() {
		pedidoFilterAtivo.setStatus(true);
		pedidosAtivosFiltrados = pedidoBo.filtrados(pedidoFilterAtivo);
	}

	public void pesquisarInativos() {
		pedidoFilterAtivo.setStatus(false);
		pedidosInativosFiltrados = pedidoBo.filtrados(pedidoFilterInativo);
	}

	public void cancelarPedido() {
		try {
			pedido.setItens(itemPedidoBo.porPedido(pedido));
			pedido.setMotivoDoCancelamento(motivo);
			pedidoBo.salvar(pedido);
			FacesUtil.addInfoMessage("Pedido Cancelado!");
			pesquisarAtivos();
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

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Pedido getPeditoDialogo() {
		return peditoDialogo;
	}

	public void setPeditoDialogo(Pedido peditoDialogo) {
		this.peditoDialogo = peditoDialogo;
	}

}
