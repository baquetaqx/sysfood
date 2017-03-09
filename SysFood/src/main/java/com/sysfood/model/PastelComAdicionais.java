package com.sysfood.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pastel_com_adicionais")
public class PastelComAdicionais implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Produto pastel;
	private List<Adicionais> adicionais;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@OneToOne
	public Produto getPastel() {
		return pastel;
	}

	public void setPastel(Produto pastel) {
		this.pastel = pastel;
	}

	@OneToMany
	public List<Adicionais> getAdicionais() {
		return adicionais;
	}

	public void setAdicionais(List<Adicionais> adicionais) {
		this.adicionais = adicionais;
	}

}
