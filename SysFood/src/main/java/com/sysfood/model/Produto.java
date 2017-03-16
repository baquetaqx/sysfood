package com.sysfood.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;

import com.sysfood.exception.NegocioException;

@Entity
public class Produto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	private BigDecimal preco;
	private Integer quantidadeEstoque = 0;
	private Boolean status = true;
	private Boolean controlarEstoque = false;
	private SecaoProduto secao;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotBlank(message = "Nome do produto é obrigatório")
	@Size(max = 80)
	@Column(nullable = false, length = 80)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@NotNull(message = "Preço é obrigatório")
	@Column(nullable = false, precision = 10, scale = 2)
	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	@Min(value = 0, message = "Quantidade não pode ser negativa")
	@Max(value = 9999, message = "Quantidade tem um valor muito alto")
	@Column(length = 5, nullable = false)
	public Integer getQuantidadeEstoque() {
		return quantidadeEstoque;
	}

	public void setQuantidadeEstoque(Integer quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}

	@Type(type = "true_false")
	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Type(type = "true_false")
	@Column(name = "controlar_estoque")
	public Boolean getControlarEstoque() {
		return controlarEstoque;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "secao_produto", nullable = false)
	public SecaoProduto getSecao() {
		return secao;
	}

	public void setSecao(SecaoProduto secao) {
		this.secao = secao;
	}

	public void setControlarEstoque(Boolean controlarEstoque) {
		this.controlarEstoque = controlarEstoque;
	}

	@Transient
	public boolean isPastel() {
		return secao == SecaoProduto.PASTEIS;
	}

	@Transient
	public boolean isNotPastel() {
		return !isPastel() && !(secao == SecaoProduto.MINI);
	}

	@Transient
	public Produto baixarEstoque(Integer quantidade) throws NegocioException {
		if (controlarEstoque) {
			int novaQuantidade = this.getQuantidadeEstoque() - quantidade;

			if (novaQuantidade < 0) {
				throw new NegocioException("Não há disponibilidade no estoque de " + quantidade + " itens do produto "
						+ this.getNome() + ".");
			}

			this.setQuantidadeEstoque(novaQuantidade);
			return this;
		}
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
