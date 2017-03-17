package com.sysfood.dao.filter;

import java.io.Serializable;
import java.util.Date;

public class PedidoFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private String cliente;
	private Date dataPedido;
	private Boolean status;

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public Date getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(Date dataPedido) {
		this.dataPedido = dataPedido;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

}
