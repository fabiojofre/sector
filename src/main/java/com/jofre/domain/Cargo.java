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
@Table(name = "cargos", indexes = {@Index(name = "idx_cargo_nome", columnList = "nome")})
public class Cargo extends AbstractEntity {
	
	@Column(name = "nome", unique = true,  nullable = false)
	private String nome;

	@Column(name = "estilo")
	private String estilo;
	
	@Column(name = "sigla")
	private String sigla;
	
	@ManyToMany
	@JoinTable(
			name = "congregados_tem_cargos", 
			joinColumns = @JoinColumn(name = "cargo_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "congregado_id", referencedColumnName = "id")
    )
	private List<Congregado> congregados;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEstilo() {
		return estilo;
	}

	public void setEstilo(String estilo) {
		this.estilo = estilo;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public List<Congregado> getCongregados() {
		return congregados;
	}

	public void setCongregados(List<Congregado> congregados) {
		this.congregados = congregados;
	}

	
	
}
