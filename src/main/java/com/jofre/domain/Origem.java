package com.jofre.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "origens", indexes = {@Index(name = "idx_origem_nome", columnList = "nome")})
public class Origem extends AbstractEntity {
	
	@Column(name = "nome", unique = true,  nullable = false)
	private String nome;
	
	@JsonIgnore
	@OneToMany(mappedBy = "origem")
	private List<Congregado> congregados;
	
		
	public String getNome() {
		return nome;
	}

	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Congregado> getCongregados() {
		return congregados;
	}

	public void setCongregados(List<Congregado> congregados) {
		this.congregados = congregados;
	}

	
		
}
