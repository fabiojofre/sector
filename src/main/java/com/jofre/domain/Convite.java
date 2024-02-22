package com.jofre.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

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
@Table(name = "convites")
public class Convite extends AbstractEntity {	
	
	@Column(name = "data_evento")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dataEvento;
	
	@Column(name = "tipo_evento")
	private Integer tipoevento;
	
	@Column(name = "area")
	private Integer area;
	
	@ManyToOne
	@JoinColumn(name= "id_congregacao")
	private Congregacao congregacao;
	
	@JsonIgnore
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
		name = "convites_tem_congregacoes", 
        joinColumns = { @JoinColumn(name = "convite_id", referencedColumnName = "id") }, 
        inverseJoinColumns = { @JoinColumn(name = "congregacao_id", referencedColumnName = "id") }
	)
	private List<Congregacao> congregacoes;
	
	@Column(name = "ativo", nullable = false)
	private Boolean ativo;
	
	
	public Convite() {
		super();
	}

	public Convite(Long id) {
		super.setId(id);
	}


	public LocalDate getDataEvento() {
		return dataEvento;
	}

	public void setDataEvento(LocalDate dataEvento) {
		this.dataEvento = dataEvento;
	}

	public Integer getTipoevento() {
		return tipoevento;
	}

	public void setTipoevento(Integer tipoevento) {
		this.tipoevento = tipoevento;
	}

	public Integer getArea() {
		return area;
	}

	public void setArea(Integer area) {
		this.area = area;
	}

	public Congregacao getCongregacao() {
		return congregacao;
	}

	public void setCongregacao(Congregacao congregacao) {
		this.congregacao = congregacao;
	}

	public List<Congregacao> getCongregacoes() {
		return congregacoes;
	}

	public void setCongregacoes(List<Congregacao> congregacoes) {
		this.congregacoes = congregacoes;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

}
