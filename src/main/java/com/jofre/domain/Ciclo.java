package com.jofre.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "ciclos")
public class Ciclo extends AbstractEntity {
	
	@Column(name = "nome", unique = true,  nullable = false)
	private String nome;

	@JsonIgnore
	@OneToMany(mappedBy = "ciclo")
	private List<Convertido> convertidos;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Convertido> getConvertidos() {
		return convertidos;
	}

	public void setConvertidos(List<Convertido> convertidos) {
		this.convertidos = convertidos;
	}
	
	
}
