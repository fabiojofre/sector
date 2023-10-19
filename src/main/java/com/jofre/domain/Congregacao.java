package com.jofre.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "congregacao")
public class Congregacao extends AbstractEntity {
	
	@Column(name = "nome",  nullable = false)
	private String nome;
	
	@Column(name = "area",  nullable = false)
	private int area;
	
	@Column(name = "e_polo")
	private boolean ePolo;

	@JsonIgnore
	@OneToMany(mappedBy = "congregacao")
	private List<Pessoa> pessoas;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getArea() {
		return area;
	}

	public void setArea(int area) {
		this.area = area;
	}

	public boolean isePolo() {
		return ePolo;
	}

	public void setePolo(boolean ePolo) {
		this.ePolo = ePolo;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}
	
	
	
	
}
