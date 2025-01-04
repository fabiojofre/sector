package com.jofre.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "licoes", indexes = { @Index(name = "idx_licao_nome", columnList = "nome") })
public class Licao extends AbstractEntity {

	@Column(name = "nome", unique = true, nullable = false)
	private String nome;


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}


}
