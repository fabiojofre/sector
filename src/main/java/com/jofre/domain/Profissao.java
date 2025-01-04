package com.jofre.domain;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;


@SuppressWarnings("serial")
@Entity
@Table(name = "profissoes", indexes = {@Index(name = "idx_profissao_nome", columnList = "nome")})
public class Profissao extends AbstractEntity {
	
	@Column(name = "nome", unique = true,  nullable = false)
	private String nome;

	@ManyToMany
	@JoinTable(
			name = "congregados_tem_profissoes", 
			joinColumns = @JoinColumn(name = "profissao_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "congregado_id", referencedColumnName = "id")
    )
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
