package com.sysfood.model;

public enum TipoPagamento {
	DINHEIRO("Dinheiro"), CREDITO("Credito"), DEBITO("Debito");
	
	private String desc;

	private TipoPagamento(String desc) {
		this.desc = desc;
	}
	
	public String getDesc() {
		return desc;
	}

}
