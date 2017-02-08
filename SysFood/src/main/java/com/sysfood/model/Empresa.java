package com.sysfood.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;

@Entity
public class Empresa implements Serializable {

	private static final long serialVersionUID = 1L;

	private Byte id = 1;
	private String razaoSocial;
	private String nomefantasia;
	private String cnpj;
	private String inscricaoEstadual;
	private String endereco;
	private String uf;
	private String cidade;

	@Id
	public Byte getId() {
		return id;
	}

	public void setId(Byte id) {
		this.id = id;
	}

	@NotBlank(message = "Razão Social é obrigatório!")
	@Column(name = "razao_social", nullable = false, length = 120)
	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	@NotBlank(message = "Nome Fantasia é obrigatório!")
	@Column(name = "nome_fantasia", nullable = false, length = 80)
	public String getNomefantasia() {
		return nomefantasia;
	}

	public void setNomefantasia(String nomefantasia) {
		this.nomefantasia = nomefantasia;
	}

	@NotBlank(message = "CNPJ é obrigatório!")
	@CNPJ(message = "CNPJ inválido!")
	@Column(nullable = false, length = 18)
	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	@NotBlank(message = "Inscrição Social é obrigatório!")
	@Column(name = "inscricao_estadual", nullable = false, length = 9)
	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}

	@NotBlank(message = "Endereço é obrigatório!")
	@Column(nullable = false, length = 50)
	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	@NotBlank(message = "UF é obrigatório!")
	@Size(max = 2)
	@Column(nullable = false, length = 2)
	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	@NotBlank(message = "Cidade é obrigatório!")
	@Column(nullable = false, length = 30)
	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
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
		Empresa other = (Empresa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
