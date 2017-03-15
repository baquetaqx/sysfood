package com.sysfood.bo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.sysfood.exception.NegocioException;
import com.sysfood.model.Caixa;
import com.sysfood.model.ItemPedido;
import com.sysfood.model.Pedido;

import nfiscal.BematechNFiscal;

public class CumpomBo implements Serializable {

	private static final long serialVersionUID = 1L;

	public void imprimirCupom(Pedido pedido) throws NegocioException {
		BematechNFiscal cupom = BematechNFiscal.Instance;
		List<Integer> iRetornos = new ArrayList<>();

		// Método para imprimir cupom para cliente
		iRetornos.add(cupom.ConfiguraModeloImpressora(7));
		iRetornos.add(cupom.IniciaPorta("COM3"));

		iRetornos.add(cupom.FormataTX("NÃO É DOCUMENTO FISCAL\n", 3, 0, 0, 0, 0));
		iRetornos.add(cupom.FormataTX("PASTEL DO CÉU\n\n", 3, 0, 0, 0, 0));

		iRetornos.add(cupom.FormataTX("FORTALEZA - CE\r\n\n", 2, 0, 0, 0, 0));
		iRetornos.add(cupom.FormataTX("Nome do Cliente: " + pedido.getCliente() + "\n", 2, 0, 0, 0, 0));
		iRetornos.add(cupom.FormataTX(
				"Data do pedido: " + new SimpleDateFormat("dd/MM/yyy - HH:mm").format(pedido.getDataPedido()) + "\n", 2,
				0, 0, 0, 0));
		iRetornos.add(cupom.FormataTX("=============================================\n", 2, 0, 0, 0, 0));
		iRetornos.add(cupom.FormataTX("Descricao\tValor\tQtds\tTotal\n", 2, 0, 0, 0, 0));
		iRetornos.add(cupom.FormataTX("=============================================\n", 2, 0, 0, 0, 0));
		for (ItemPedido item : pedido.getItens()) {
			iRetornos.add(cupom.FormataTX(item.getProduto().getNome() + "\tR$" + item.getValorUnitario() + "\t"
					+ item.getQuantidade() + "\tR$" + item.getValorTotal() + "\n", 2, 0, 0, 0, 0));
		}
		iRetornos.add(cupom.FormataTX("=============================================\n", 2, 0, 0, 0, 0));
		iRetornos.add(cupom.FormataTX("Total: " + pedido.getValorTotal() + "\n", 2, 0, 0, 0, 0));

		iRetornos.add(cupom.FormataTX("---------------------------------------------\n", 2, 0, 0, 0, 0));
		iRetornos.add(cupom.FormataTX("OBRIGADO\n", 2, 0, 0, 0, 0));
		iRetornos.add(cupom.FormataTX("VOLTE SEMPRE\n", 2, 0, 0, 0, 0));
		iRetornos.add(cupom.FormataTX("---------------------------------------------\n", 2, 0, 0, 0, 0));

		iRetornos.add(cupom.AcionaGuilhotina(1));

		// Método para controle de cozinha
		iRetornos.add(cupom.FormataTX("PASTEL DO CÉU\n\n", 3, 0, 0, 0, 0));
		iRetornos.add(cupom.FormataTX("CONTROLE DA COZINHA\n", 2, 0, 0, 0, 0));
		iRetornos.add(cupom.FormataTX("Nome do Cliente: " + pedido.getCliente().toUpperCase() + "\n", 2, 0, 0, 0, 0));
		iRetornos.add(cupom.FormataTX(
				"Data do pedido: " + new SimpleDateFormat("dd/MM/yyy - HH:mm").format(pedido.getDataPedido()) + "\n", 2,
				0, 0, 0, 0));
		iRetornos.add(cupom.FormataTX("=============================================\n", 2, 0, 0, 0, 0));
		iRetornos.add(cupom.FormataTX("DESCRIÇÃO\n", 2, 0, 0, 0, 0));
		iRetornos.add(cupom.FormataTX("=============================================\n", 2, 0, 0, 0, 0));

		for (ItemPedido item : pedido.getItens()) {
			iRetornos.add(cupom.FormataTX(
					"Qtd = " + item.getQuantidade() + "/" + item.getProduto().getNome() + "\n" + "Adicionais: - " , 2, 0,
					0, 0, 0));
		}

		iRetornos.add(cupom.FormataTX("=============================================\n", 2, 0, 0, 0, 0));
		iRetornos.add(cupom.AcionaGuilhotina(1));

		// Fechar Porta
		iRetornos.add(cupom.FechaPorta());

		for (Integer integer : iRetornos) {
			if (integer == 0) {
				throw new NegocioException("Erro de comunicação física.");
			}
		}

	}

	public void imprimirCaixa(Caixa caixa) throws NegocioException {
		BematechNFiscal cupom = BematechNFiscal.Instance;
		List<Integer> iRetornos = new ArrayList<>();
		iRetornos.add(cupom.ConfiguraModeloImpressora(7));
		iRetornos.add(cupom.IniciaPorta("COM3"));

		iRetornos.add(cupom.FormataTX("PASTEL DO CÉU - RELATÓRIO DE CAIXA\n\n\n", 3, 0, 0, 0, 0));
		iRetornos.add(cupom.FormataTX("---------------------------------------------\n", 2, 0, 0, 0, 0));
		iRetornos
				.add(cupom.FormataTX(
						"Data de abertura:\t "
								+ new SimpleDateFormat("dd/MM/yyy - HH:mm").format(caixa.getDataDeAbertura()) + "\n",
						2, 0, 0, 0, 0));
		iRetornos.add(cupom.FormataTX("Fundo de caixa:\t" + caixa.getFundoDeCaixa() + "\n", 2, 0, 0, 0, 0));
		iRetornos.add(cupom.FormataTX("Débito:\t" + caixa.getDebito() + "\n", 2, 0, 0, 0, 0));
		iRetornos.add(cupom.FormataTX("Crédito:\t" + caixa.getCredito() + "\n", 2, 0, 0, 0, 0));
		iRetornos.add(cupom.FormataTX("Dinheiro:\t" + caixa.getDinheiro() + "\n", 2, 0, 0, 0, 0));
		iRetornos.add(cupom.FormataTX("Número de pedidos:\t" + caixa.getNumeroDePedidos() + "\n", 2, 0, 0, 0, 0));
		iRetornos.add(cupom.FormataTX("Total:\t" + caixa.getTotal() + "\n", 2, 0, 0, 0, 0));
		iRetornos.add(cupom.FormataTX("---------------------------------------------\n", 2, 0, 0, 0, 0));
		for (Integer integer : iRetornos) {
			if (integer == 0) {
				throw new NegocioException("Erro de comunicação física.");
			}
		}
	}

}
