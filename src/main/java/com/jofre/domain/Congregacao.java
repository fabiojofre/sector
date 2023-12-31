package com.jofre.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "congregacoes", indexes = {@Index(name = "idx_congregacao_nome", columnList = "nome")})
public class Congregacao extends AbstractEntity {
	
	@Column(name = "nome", unique = true,  nullable = false)
	private String nome;
	
	@Column(name = "area")
	private Integer area;
	
	@Column(name = "responsavel")
	private String responsavel;
	
	@Column(name = "e_polo")
	private Boolean ePolo;
	
	@Column(name = "dia_doutrina")
	private Integer diaDoutrina;
	
	@Column(name = "dia_oracao_mocidade")
	private Integer diaOracaoMocidade;
	
	@Column(name = "dia_discipulado")
	private Integer diaDiscipulado;
	
	@Column(name = "semana_ceia")
	private Integer semanaCeia;
	
	@Column(name = "ebd_sabado")
	private Boolean ebdSabado;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getArea() {
		return area;
	}

	public void setArea(Integer area) {
		this.area = area;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public Boolean getePolo() {
		return ePolo;
	}

	public void setePolo(Boolean ePolo) {
		this.ePolo = ePolo;
	}

	public Integer getDiaDoutrina() {
		return diaDoutrina;
	}

	public void setDiaDoutrina(Integer diaDoutrina) {
		this.diaDoutrina = diaDoutrina;
	}

	public Integer getDiaOracaoMocidade() {
		return diaOracaoMocidade;
	}

	public void setDiaOracaoMocidade(Integer diaOracaoMocidade) {
		this.diaOracaoMocidade = diaOracaoMocidade;
	}

	public Integer getDiaDiscipulado() {
		return diaDiscipulado;
	}

	public void setDiaDiscipulado(Integer diaDiscipulado) {
		this.diaDiscipulado = diaDiscipulado;
	}

	public Integer getSemanaCeia() {
		return semanaCeia;
	}

	public void setSemanaCeia(Integer semanaCeia) {
		this.semanaCeia = semanaCeia;
	}

	public Boolean getEbdSabado() {
		return ebdSabado;
	}

	public void setEbdSabado(Boolean ebdSabado) {
		this.ebdSabado = ebdSabado;
	}
	


		
}
