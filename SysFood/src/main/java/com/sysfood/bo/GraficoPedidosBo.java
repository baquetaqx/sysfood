package com.sysfood.bo;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.inject.Inject;

import com.sysfood.dao.PedidoDao;

public class GraficoPedidosBo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private PedidoDao pedidoDao;
	
	public Map<Date, Integer> quantidadeDeProdutosPorData(Integer numeroDeDias) {
		return null;
	}

}
