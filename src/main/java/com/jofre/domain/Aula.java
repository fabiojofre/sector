package com.jofre.domain;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "aulas")
public class Aula extends AbstractEntity {
	
	@ManyToOne
	@JoinColumn(name="id_licao")
	private Licao licao;
	
	@Column(name = "data_aula")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dataAula;
	
	@Column(name = "data_lancamento")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dataLancamento;
	
	@ManyToOne
	@JoinColumn(name="id_pessoa")
	private Pessoa pessoa;
	
	@Column(name = "status")
	private Integer status;
	
	@ManyToOne
	@JoinColumn(name="id_professor")
	private Professor professor;
	
	@ManyToOne
	@JoinColumn(name="id_congregacao")
	private Congregacao congregacao;
	
	@ManyToMany
	@JoinTable(
			name = "convertidos_tem_aulas",
			joinColumns = @JoinColumn(name = "id_aula", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "id_convertido", referencedColumnName = "id")
    )
	private Set<Convertido> convertidos;

	public Licao getLicao() {
		return licao;
	}

	public void setLicao(Licao licao) {
		this.licao = licao;
	}

	public LocalDate getDataAula() {
		return dataAula;
	}

	public void setDataAula(LocalDate dataAula) {
		this.dataAula = dataAula;
	}

	public LocalDate getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(LocalDate dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	
	public Pessoa getPessoa() {
		return pessoa;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Set<Convertido> getConvertidos() {
		return convertidos;
	}

	public void setConvertidos(Set<Convertido> convertidos) {
		this.convertidos = convertidos;
	}

	public Congregacao getCongregacao() {
		return congregacao;
	}

	public void setCongregacao(Congregacao congregacao) {
		this.congregacao = congregacao;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}	

	
}
