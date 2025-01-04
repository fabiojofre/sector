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
@Table(name = "grupos", indexes = {@Index(name = "idx_grupo_descricao", columnList = "descricao")})
public class Grupo extends AbstractEntity {
	
	@Column(name = "descricao", nullable = false, unique = true)
	private String descricao;
	
	@JsonIgnore
	@OneToMany(mappedBy = "grupo")
	List<Produto>produtos;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
	

}
