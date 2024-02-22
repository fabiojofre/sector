package com.jofre.dto;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * dto para formulario ajax
 */
public class ConviteDto implements Serializable {
    
	
	private LocalDate dataEvento;
	private Integer tipoevento;
	private Integer area;
	private String congregacao;
	private String[] congregacoes;
	private Boolean ativo;
	
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
	public String getCongregacao() {
		return congregacao;
	}
	public void setCongregacao(String congregacao) {
		this.congregacao = congregacao;
	}
	public String[] getCongregacoes() {
		return congregacoes;
	}
	public void setCongregacoes(String[] congregacoes) {
		this.congregacoes = congregacoes;
	}
	public Boolean getAtivo() {
		return ativo;
	}
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	
		
}
