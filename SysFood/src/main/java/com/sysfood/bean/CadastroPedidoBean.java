package com.sysfood.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.sysfood.bo.AdicionalBO;
import com.sysfood.bo.PedidoBo;
import com.sysfood.bo.ProdutoBo;
import com.sysfood.dao.filter.ProdutoFilter;
import com.sysfood.exception.NegocioException;
import com.sysfood.model.Adicional;
import com.sysfood.model.ItemPedido;
import com.sysfood.model.Pedido;
import com.sysfood.model.Produto;
import com.sysfood.model.TipoPagamento;
import com.sysfood.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroPedidoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Pedido pedido;
	private ProdutoFilter produtoFilter;
	private List<Produto> produtosFiltrados;
	private Produto produto;
	private BigDecimal valorPago;
	private BigDecimal troco;
	private List<Adicional> adicionalFiltrados;
	private Adicional adicionalFiltro;
	private Adicional adicional;
	private Map<Produto, List<Adicional>> pastelComAdicionai;

	@Inject
	private ProdutoBo produtoBo;

	@Inject
	private PedidoBo pedidoBo;

	@Inject
	private AdicionalBO adicionalBo;

	public CadastroPedidoBean() {
		limpar();
	}

	private void limpar() {
		setPedido(new Pedido());
		produtoFilter = new ProdutoFilter();
		produto = new Produto();
		valorPago = null;
		troco = null;
		adicionalFiltro = new Adicional();
		adicional = new Adicional();
	}

	public void salvar() {
		try {
			this.pedido = pedidoBo.salvar(this.pedido);
			limpar();

			FacesUtil.addInfoMessage("Pedido efetuado com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}

	}

	public void pesquisarProduto() {
		produtoFilter.setStatus(true);
		produtosFiltrados = produtoBo.filtrados(produtoFilter);
	}

	public void pesquisarAdicional() {
		adicionalFiltrados = adicionalBo.filtrados(adicionalFiltro);
	}

	public void adicionarItem() {
		if (existeItemComProduto(produto)) {
			FacesUtil.addErrorMessage("Já existe um item no pedido com o produto informado.");
		} else {
			ItemPedido item = new ItemPedido();
			item.setPedido(pedido);
			item.setProduto(produto);
			item.setValorUnitario(produto.getPreco());
			pedido.getItens().add(item);
			recalcularPedido();
		}
	}

	public void adicionarAdicionalAoPastel() {
		if (pastelComAdicionai.containsKey(produto)) {

		}
		pastelComAdicionai.put(produto, new ArrayList<>());
	}

	private boolean existeItemComProduto(Produto produto) {
		boolean existeItem = false;

		for (ItemPedido item : this.getPedido().getItens()) {
			if (produto.equals(item.getProduto())) {
				existeItem = true;
				break;
			}
		}

		return existeItem;
	}

	public TipoPagamento[] getTipoPagament() {
		return TipoPagamento.values();
	}

	public void removerItem(ItemPedido itemPedido) {
		pedido.getItens().remove(itemPedido);
		recalcularPedido();
		FacesUtil.addInfoMessage(itemPedido.getProduto().getNome() + " Removido");
	}

	public void recalcularPedido() {
		if (pedido != null) {
			pedido.recalcularValorTotal();
		}
	}

	public void calcularTroco() {
		troco = valorPago.subtract(pedido.getValorTotal());
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public ProdutoFilter getProdutoFilter() {
		return produtoFilter;
	}

	public List<Produto> getProdutosFiltrados() {
		return produtosFiltrados;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public BigDecimal getValorPago() {
		return valorPago;
	}

	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
	}

	public BigDecimal getTroco() {
		return troco;
	}

	public void setTroco(BigDecimal troco) {
		this.troco = troco;
	}

	public List<Adicional> getAdicionalFiltrados() {
		return adicionalFiltrados;
	}

	public Adicional getAdicionalFiltro() {
		return adicionalFiltro;
	}

}