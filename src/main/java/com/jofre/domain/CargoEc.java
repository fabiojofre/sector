package com.jofre.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@SuppressWarnings("serial")
@Entity
@Table(name = "cargo_ec")
public class CargoEc extends AbstractEntity{

	@Column(nullable =  false, length = 60, unique = true)
	private String nome;

	
	@Column(nullable =  false)
	private String sigla;
	
	
	
	@OneToMany(mappedBy = "cargoEc")
	private List<Obreiro> obreiros = new ArrayList<>();



	public String getNome() {
		return nome;
	}



	public String getSigla() {
		return sigla;
	}



	public List<Obreiro> getObreiros() {
		return obreiros;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	public void setSigla(String sigla) {
		this.sigla = sigla;
	}



	public void setObreiros(List<Obreiro> obreiros) {
		this.obreiros = obreiros;
	}

	
}
