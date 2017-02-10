package com.sysfood.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.sysfood.bo.ProdutoBo;
import com.sysfood.dao.filter.ProdutoFilter;
import com.sysfood.model.ItemPedido;
import com.sysfood.model.Pedido;
import com.sysfood.model.Produto;

@Named
@ViewScoped
public class CadastroPedidoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Pedido pedido;
	private String nomeCliente;
	private ProdutoFilter produtoFilter;
	private List<Produto> produtosFiltrados;
	private Produto produto;

	@Inject
	private ProdutoBo produtoBo;

	public CadastroPedidoBean() {
		setPedido(new Pedido());
		produtoFilter = new ProdutoFilter();
		produto = new Produto();
	}

	public void adicionarCliente() {
		pedido.setCliente(nomeCliente);
	}

	public void pesquisarProduto() {
		produtosFiltrados = produtoBo.filtrados(produtoFilter);
	}

	public void adicionarItem() {
		ItemPedido item = new ItemPedido();
		item.setPedido(pedido);
		item.setProduto(produto);
		item.setValorUnitario(produto.getPreco());
		pedido.getItens().add(item);
	}

	public void removerItem(ItemPedido item) {
		pedido.getItens().remove(item);
	}

	public void recalcularPedido() {
		if (pedido != null) {
			pedido.recalcularValorTotal();
		}
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
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

}
