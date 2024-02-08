package com.jofre.domain;

import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@SuppressWarnings("serial")
@Entity
@Table(name = "evento_campanha")
public class EventoCampanha extends AbstractEntity{
	
	@Column(name = "tipo_evento",  nullable = false)
	private String tipoEvento;
	
	@Column(name = "data", nullable = false )
	private LocalDate data;
	
	@ManyToOne
	@JoinColumn(name= "id_congregacao")
	private Congregacao congregacao;
	
	// evita recursividade quando o json de resposta for criado para a datatables.
	@JsonIgnore
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
			name = "campanha_tem_evento",
			joinColumns = @JoinColumn(name = "id_congregacao", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "id_evento_campanha", referencedColumnName = "id")
    )
private Set<Congregacao>congregacoes;

	public String getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public Congregacao getCongregacao() {
		return congregacao;
	}

	public void setCongregacao(Congregacao congregacao) {
		this.congregacao = congregacao;
	}

	public Set<Congregacao> getCongregacoes() {
		return congregacoes;
	}

	public void setCongregacoes(Set<Congregacao> congregacoes) {
		this.congregacoes = congregacoes;
	}


}
