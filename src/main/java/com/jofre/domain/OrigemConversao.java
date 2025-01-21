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
@Table(name = "origem_conversao", indexes = {@Index(name = "idx_origem_conversao_nome", columnList = "nome")})
public class OrigemConversao extends AbstractEntity {
	
	@Column(name = "nome", unique = true,  nullable = false)
	private String nome;
	
	@Column(name = "inativo")
	private String inativo;
	
	@JsonIgnore
	@OneToMany(mappedBy = "origem_conversao")
	private List<Convertido> convertidos;
	
		
	public String getNome() {
		return nome;
	}

	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	

	public String getInativo() {
		return inativo;
	}


	public void setInativo(String inativo) {
		this.inativo = inativo;
	}


	public List<Convertido> getConvertidos() {
		return convertidos;
	}

	public void setConvertidos(List<Convertido> convertidos) {
		this.convertidos = convertidos;
	}

	
		
}
