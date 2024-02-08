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
@Table(name = "aulas", indexes = {@Index(name = "idx_aula", columnList = "aula")})
public class Aula extends AbstractEntity {
	
	@Column(name = "aula", unique = true, nullable = false)
	private String aula;
	
	@ManyToMany
	@JoinTable(
			name = "discipulado_tem_aulas",
			joinColumns = @JoinColumn(name = "id_aula", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "id_discipulado", referencedColumnName = "id")
    )
	private List<Discipulado> discipulados;	

	public String getAula() {
		return aula;
	}

	public void setAula(String aula) {
		this.aula = aula;
	}

	public List<Discipulado> getDiscipulados() {
		return discipulados;
	}

	public void setDiscipulados(List<Discipulado> discipulados) {
		this.discipulados = discipulados;
	}


}
