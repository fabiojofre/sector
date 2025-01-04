package com.jofre.domain;

import java.time.LocalDate;
import java.util.Set;

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
@Table(name = "convertidos")
public class Convertido extends AbstractEntity{
	
	@Column(name = "nome", unique = false,  nullable = false)
	private String nome;
	
	@Column(name = "convertido")
	private Boolean convertido;
	
	@Column(name = "inativo")
	private Boolean inativo;
	
	@Column(name = "obs_conversao")
	private String obsConversao;
	
	@Column(name = "telefone")
	private String telefone;
	
	@Column(name = "endereco")
	private String endereco;
	
	@Column(name="area")
	private Integer area;

	@Column(name="da_uniao")
	private Boolean daUniao;
	
	@Column(name="matriculado")
	private Boolean matriculado;
	
	@Column(name="concluinte")
	private Boolean concluinte;
	
	@Column(name="batismo")
	private Boolean batismo;
	
	@ManyToOne
	@JoinColumn(name= "id_congregacao")
	private Congregacao congregacao;
	
	@ManyToOne
	@JoinColumn(name= "id_estadocivil")
	private EstadoCivil estadocivil;
	
	
	@ManyToOne
	@JoinColumn(name="id_pessoa")
	private Pessoa pessoa;
	
	@Column(name = "data_nascimento")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dataNascimento;
	
	@Column(name = "data_conversao")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dataConversao;
	
	@Column(name = "data_conclusao")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dataConclusao;
	
	@Column(name = "data_matriculado")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dataMatriculado;
	

	// evita recursividade quando o json de resposta for criado para a datatables.
			@JsonIgnore
			@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
			@JoinTable(
					name = "convertidos_tem_aulas",
					joinColumns = @JoinColumn(name = "id_convertido", referencedColumnName = "id"),
					inverseJoinColumns = @JoinColumn(name = "id_aula", referencedColumnName = "id")
		    )
		private Set<Aula>aulas;
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Boolean getConvertido() {
		return convertido;
	}

	public void setConvertido(Boolean convertido) {
		this.convertido = convertido;
	}

	public Boolean getInativo() {
		return inativo;
	}

	public void setInativo(Boolean inativo) {
		this.inativo = inativo;
	}

	public String getObsConversao() {
		return obsConversao;
	}

	public void setObsConversao(String obsConversao) {
		this.obsConversao = obsConversao;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Congregacao getCongregacao() {
		return congregacao;
	}

	public void setCongregacao(Congregacao congregacao) {
		this.congregacao = congregacao;
	}


	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public LocalDate getDataConversao() {
		return dataConversao;
	}

	public void setDataConversao(LocalDate dataConversao) {
		this.dataConversao = dataConversao;
	}

	public Integer getArea() {
		return area;
	}

	public void setArea(Integer area) {
		this.area = area;
	}

	public Boolean getDaUniao() {
		return daUniao;
	}

	public void setDaUniao(Boolean daUniao) {
		this.daUniao = daUniao;
	}

	public Boolean getMatriculado() {
		return matriculado;
	}

	public void setMatriculado(Boolean matriculado) {
		this.matriculado = matriculado;
	}

	public Boolean getConcluinte() {
		return concluinte;
	}

	public void setConcluinte(Boolean concluinte) {
		this.concluinte = concluinte;
	}

	public Boolean getBatismo() {
		return batismo;
	}

	public void setBatismo(Boolean batismo) {
		this.batismo = batismo;
	}

	public LocalDate getDataConclusao() {
		return dataConclusao;
	}

	public void setDataConclusao(LocalDate dataConclusao) {
		this.dataConclusao = dataConclusao;
	}

	public EstadoCivil getEstadocivil() {
		return estadocivil;
	}

	public void setEstadocivil(EstadoCivil estadocivil) {
		this.estadocivil = estadocivil;
	}


	public LocalDate getDataMatriculado() {
		return dataMatriculado;
	}

	public void setDataMatriculado(LocalDate dataMatriculado) {
		this.dataMatriculado = dataMatriculado;
	}

	public Set<Aula> getAulas() {
		return aulas;
	}

	public void setAulas(Set<Aula> aulas) {
		this.aulas = aulas;
	}
	
	
}