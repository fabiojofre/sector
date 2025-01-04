package com.jofre.domain;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "professores")
public class Professor extends AbstractEntity{
	
	
	@Column(name = "nome", length = 60, unique = false,  nullable = false)
	private String nome;
	
	@Column(name = "inativo")
	private Boolean inativo;
	
	@Column(name = "telefone")
	private Long telefone;

	@Column(name = "cartao_membro")
	private Long cartaoMembro;
	
	@Column(name = "data_nascimento")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dataNascimento;
	
	@Column(name = "data_cadastro")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDate dataCadastro;
	
	@ManyToOne
	@JoinColumn(name= "id_congregacao")
	private Congregacao congregacao;
	
	@ManyToOne
	@JoinColumn(name= "id_grau")
	private Grau grau;
	
	@ManyToOne
	@JoinColumn(name= "id_estadocivil")
	private EstadoCivil estadocivil;
	
	@ManyToOne
	@JoinColumn(name = "id_pessoa")
	private Pessoa pessoa;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getTelefone() {
		return telefone;
	}

	public void setTelefone(Long telefone) {
		this.telefone = telefone;
	}

	public Long getCartaoMembro() {
		return cartaoMembro;
	}

	public void setCartaoMembro(Long cartaoMembro) {
		this.cartaoMembro = cartaoMembro;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public LocalDate getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Congregacao getCongregacao() {
		return congregacao;
	}

	public void setCongregacao(Congregacao congregacao) {
		this.congregacao = congregacao;
	}

	public Grau getGrau() {
		return grau;
	}

	public void setGrau(Grau grau) {
		this.grau = grau;
	}

	public EstadoCivil getEstadocivil() {
		return estadocivil;
	}

	public void setEstadocivil(EstadoCivil estadocivil) {
		this.estadocivil = estadocivil;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Boolean getInativo() {
		return inativo;
	}

	public void setInativo(Boolean inativo) {
		this.inativo = inativo;
	}
	
	
}
