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
	
	@Column(name="dia_ebd")
	private Integer diaEbd;
	
		
	@JsonIgnore
	@OneToMany(mappedBy = "congregacao")
	private List<Convertido> convertidos;
	
	@JsonIgnore
	@OneToMany(mappedBy = "congregacao")
	private List<Convite> convites;
	
	@JsonIgnore
	@OneToMany(mappedBy = "congregacao")
	private List<Pessoa> pessoas;


	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

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


	public Integer getDiaEbd() {
		return diaEbd;
	}

	public void setDiaEbd(Integer diaEbd) {
		this.diaEbd = diaEbd;
	}

	public List<Convite> getConvites() {
		return convites;
	}

	public void setConvites(List<Convite> convites) {
		this.convites = convites;
	}

	public List<Convertido> getConvertidos() {
		return convertidos;
	}

	public void setConvertidos(List<Convertido> convertidos) {
		this.convertidos = convertidos;
	}
	


		
}
