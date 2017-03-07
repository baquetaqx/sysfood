package com.sysfood.bo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.OptionalInt;

import com.sysfood.model.ItemPedido;
import com.sysfood.model.Pedido;

import nfiscal.BematechNFiscal;

public class CumpomBo implements Serializable {

	private static final long serialVersionUID = 1L;

	public void imprimirCupom(Pedido pedido) {
		BematechNFiscal cupom = BematechNFiscal.Instance;

		cupom.ConfiguraModeloImpressora(7);
		cupom.IniciaPorta("COM3");

		cupom.FormataTX("NÃO É DOCUMENTO FISCAL\n", 3, 0, 0, 0, 0);
		cupom.FormataTX("PASTEL DO CÉU\n\n", 3, 0, 0, 0, 0);

		cupom.FormataTX("FORTALEZA - CE\r\n\n", 2, 0, 0, 0, 0);
		cupom.FormataTX("Nome do Cliente: " + pedido.getCliente() + "\n", 2, 0, 0, 0, 0);
		cupom.FormataTX(
				"Data do pedido: " + new SimpleDateFormat("dd/MM/yyy - HH:mm").format(pedido.getDataPedido()) + "\n", 2,
				0, 0, 0, 0);
		cupom.FormataTX("=============================================\n", 2, 0, 0, 0, 0);
		cupom.FormataTX("Descricao\tValor\tQtds\tTotal\n", 2, 0, 0, 0, 0);
		cupom.FormataTX("=============================================\n", 2, 0, 0, 0, 0);
		for (ItemPedido item : pedido.getItens()) {
			cupom.FormataTX(item.getProduto().getNome() + "\tR$" + item.getValorUnitario() + "\t" + item.getQuantidade()
					+ "\tR$" + item.getValorTotal() + "\n", 2, 0, 0, 0, 0);
		}
		cupom.FormataTX("=============================================\n", 2, 0, 0, 0, 0);
		cupom.FormataTX("Total: " + pedido.getValorTotal() + "\n", 2, 0, 0, 0, 0);

		cupom.FormataTX("---------------------------------------------\n", 2, 0, 0, 0, 0);
		cupom.FormataTX("OBRIGADO\n", 2, 0, 0, 0, 0);
		cupom.FormataTX("VOLTE SEMPRE\n", 2, 0, 0, 0, 0);
		cupom.FormataTX("---------------------------------------------\n", 2, 0, 0, 0, 0);

		cupom.AcionaGuilhotina(1);
		cupom.FechaPorta();
	}

}
