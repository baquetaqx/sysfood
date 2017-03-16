package com.sysfood.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.sysfood.bo.AdicionalBO;
import com.sysfood.bo.PedidoBo;
import com.sysfood.bo.ProdutoBo;
import com.sysfood.dao.filter.ProdutoFilter;
import com.sysfood.exception.NegocioException;
import com.sysfood.model.Adicional;
import com.sysfood.model.ItemPedido;
import com.sysfood.model.Pedido;
import com.sysfood.model.Produto;
import com.sysfood.model.SecaoProduto;
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
	private Map<Produto, List<Adicional>> pastelComAdicionais;

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
		setAdicional(new Adicional());
		pastelComAdicionais = new HashMap<>();
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
			item.setPastelComAdicionais(pastelComAdicionais);
			item.setPastelComAdicionais(pastelComAdicionais);
			pedido.getItens().add(item);
			recalcularPedido();
		}
		pastelComAdicionais = new HashMap<>();

	}
	
	public void verificarPastel(){
		if ((produto.getSecao() == SecaoProduto.PASTEIS) && !pastelComAdicionais.containsKey(produto)) {
			RequestContext.getCurrentInstance().execute("PF('dlgAdicional').show()");
		}	else{
			adicionarItem();
		}
	}
	


	public void adicionarAdicionalAoPastel() {
		if (existePastelComAdicionais(produto, adicional)) {
			FacesUtil.addErrorMessage("Já existe um pastel com o adiconal informado.");
		} else if (!pastelComAdicionais.containsKey(produto)) {
			List<Adicional> adicionais = new ArrayList<>();
			adicionais.add(adicional);
			pastelComAdicionais.put(produto, adicionais);
		} else {
			pastelComAdicionais.get(produto).add(adicional);
		}
	}

	private boolean existePastelComAdicionais(Produto produto, Adicional adicional) {
		boolean existePastel = false;
		if (pastelComAdicionais.containsKey(produto)) {
			existePastel = pastelComAdicionais.get(produto).contains(adicional);
		}
		return existePastel;
	}

	private boolean existeItemComProduto(Produto produto) {
		boolean existeItem = false;

		for (ItemPedido item : this.getPedido().getItens()) {
			if (produto.equals(item.getProduto()) && !item.getPastelComAdicionais().containsKey(produto)
					&& pastelComAdicionais.isEmpty()) {
				existeItem = true;
				break;
			} else if (item.getPastelComAdicionais().containsKey(produto)
					&& item.getPastelComAdicionais().get(produto).equals(pastelComAdicionais.get(produto))) {
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
		try {
			troco = valorPago.subtract(pedido.getValorTotal());
		} catch (NullPointerException e) {
			troco = BigDecimal.ZERO;
		}
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

	public Adicional getAdicional() {
		return adicional;
	}

	public void setAdicional(Adicional adicional) {
		this.adicional = adicional;
	}

	public Map<Produto, List<Adicional>> getPastelComAdicionais() {
		return pastelComAdicionais;
	}

	public List<Adicional> adicionaisNoPastel(Produto produto) {
		return pastelComAdicionais.get(produto);
	}

	public boolean pastelComAdicionais(Produto produto) {
		return pastelComAdicionais.containsKey(produto);
	}

	public void setPastelComAdicionais(Map<Produto, List<Adicional>> pastelComAdicionais) {
		this.pastelComAdicionais = pastelComAdicionais;
	}

	public SecaoProduto[] getSecao() {
		return SecaoProduto.values();
	}

}