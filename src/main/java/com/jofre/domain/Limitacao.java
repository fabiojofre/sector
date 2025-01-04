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
@Table(name = "limitacoes", indexes = {@Index(name = "idx_limitacao_nome", columnList = "nome")})
public class Limitacao extends AbstractEntity {
	
	@Column(name = "nome", unique = true,  nullable = false)
	private String nome;

	@JsonIgnore
	@OneToMany(mappedBy = "limitacao")
	private List<Congregado> congregados;
	
	
	
	public List<Congregado> getCongregados() {
		return congregados;
	}

	public void setCongregados(List<Congregado> congregados) {
		this.congregados = congregados;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
