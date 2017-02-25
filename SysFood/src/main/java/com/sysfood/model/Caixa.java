package com.sysfood.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

@Entity
public class Caixa implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Date dataDeAbertura;
	private BigDecimal fundoDeCaixa;
	private BigDecimal debito;
	private BigDecimal credito;
	private BigDecimal dinheiro;
	private Integer numeroDePedidos;
	private BigDecimal total;
	private Boolean caixaAberto;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "data_de_abertura")
	@Temporal(TemporalType.DATE)
	public Date getDataDeAbertura() {
		return dataDeAbertura;
	}

	public void setDataDeAbertura(Date dataDeAbertura) {
		this.dataDeAbertura = dataDeAbertura;
	}

	@Column(name = "fundo_de_caixa", precision = 10, scale = 2)
	public BigDecimal getFundoDeCaixa() {
		return fundoDeCaixa;
	}

	public void setFundoDeCaixa(BigDecimal fundoDeCaixa) {
		this.fundoDeCaixa = fundoDeCaixa;
	}

	@Column(precision = 10, scale = 2)
	public BigDecimal getDebito() {
		return debito;
	}

	public void setDebito(BigDecimal debito) {
		this.debito = debito;
	}

	@Column(precision = 10, scale = 2)
	public BigDecimal getCredito() {
		return credito;
	}

	public void setCredito(BigDecimal credito) {
		this.credito = credito;
	}

	@Column(precision = 10, scale = 2)
	public BigDecimal getDinheiro() {
		return dinheiro;
	}

	public void setDinheiro(BigDecimal dinheiro) {
		this.dinheiro = dinheiro;
	}

	@Column(name = "numero_de_pedidos", length = 3)
	public Integer getNumeroDePedidos() {
		return numeroDePedidos;
	}

	public void setNumeroDePedidos(Integer numeroDePedidos) {
		this.numeroDePedidos = numeroDePedidos;
	}

	@Column(precision = 10, scale = 2)
	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	@Type(type = "true_false")
	public Boolean getCaixaAberto() {
		return caixaAberto;
	}

	public void setCaixaAberto(Boolean caixaAberto) {
		this.caixaAberto = caixaAberto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataDeAbertura == null) ? 0 : dataDeAbertura.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Caixa other = (Caixa) obj;
		if (dataDeAbertura == null) {
			if (other.dataDeAbertura != null)
				return false;
		} else if (!dataDeAbertura.equals(other.dataDeAbertura))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
