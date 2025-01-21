package com.jofre.domain;

import java.time.LocalDate;
import java.util.Set;

import org.hibernate.validator.constraints.br.CPF;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "congregados")
public class Congregado extends AbstractEntity{
	
	@Column(name = "nome", length = 60, unique = false,  nullable = false)
	private String nome;
	
	@Column(name = "endereco")
	private String endereco;
	
	@Column(name = "telefone")
	private Long telefone;
	
	
	@CPF(message = "CPF inválido.")
	@Column(name = "cpf",length = 15 ,unique = true,  nullable = false)
	private String cpf;
	
	@Column(name = "rg",unique = false,  nullable = true)
	private Long rg;
	
	@Column(name = "cartao_membro")
	private Long cartaoMembro;
	
	@Column(name = "data_nascimento")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dataNascimento;
	
	@Column(name = "data_batismo")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dataBatismo;
	
	@Column(name = "data_cadastro")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDate dataCadastro;
	
	@Column(name = "data_alteracao")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDate dataAlteracao;
	
	@ManyToOne
	@JoinColumn(name= "id_origem")
	private Origem origem;
	
	@ManyToOne
	@JoinColumn(name= "id_limitacao")
	private Limitacao limitacao;
	
	@Column(name = "data_mudanca")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dataMudanca;
	
	@ManyToOne
	@JoinColumn(name= "id_congregacao")
	private Congregacao congregacao;
	
	@ManyToOne
	@JoinColumn(name= "id_grau")
	private Grau grau;
	
	@ManyToOne
	@JoinColumn(name= "id_estadocivil")
	private EstadoCivil estadocivil;
	
	@OneToOne()
	@JoinColumn(name = "id_usuariocadastro")
	private Usuario usuariocadastro;
	
	@OneToOne()
	@JoinColumn(name = "id_usuarioalteracao")
	private Usuario usuarioalteracao;

	
	// tem que apear tanto de uma classe como de outra
	@JsonIgnore
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
		name = "congregados_tem_cargos", 
        joinColumns = { @JoinColumn(name = "congregado_id", referencedColumnName = "id") }, 
        inverseJoinColumns = { @JoinColumn(name = "cargo_id", referencedColumnName = "id") })
	private Set <Cargo> cargos;
	
	
	@JsonIgnore
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
		name = "congregados_tem_profissoes", 
        joinColumns = { @JoinColumn(name = "congregado_id", referencedColumnName = "id") }, 
        inverseJoinColumns = { @JoinColumn(name = "profissao_id", referencedColumnName = "id") })
	private Set <Profissao> profissoes;
	
	@Column(name = "profissional")
	private String profissional;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Long getTelefone() {
		return telefone;
	}

	public void setTelefone(Long telefone) {
		this.telefone = telefone;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Long getRg() {
		return rg;
	}

	public void setRg(Long rg) {
		this.rg = rg;
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

	public LocalDate getDataBatismo() {
		return dataBatismo;
	}

	public void setDataBatismo(LocalDate dataBatismo) {
		this.dataBatismo = dataBatismo;
	}

	public LocalDate getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public LocalDate getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(LocalDate dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	public Origem getOrigem() {
		return origem;
	}

	public void setOrigem(Origem origem) {
		this.origem = origem;
	}

	public Limitacao getLimitacao() {
		return limitacao;
	}

	public void setLimitacao(Limitacao limitacao) {
		this.limitacao = limitacao;
	}

	public LocalDate getDataMudanca() {
		return dataMudanca;
	}

	public void setDataMudanca(LocalDate dataMudanca) {
		this.dataMudanca = dataMudanca;
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

	public Usuario getUsuariocadastro() {
		return usuariocadastro;
	}

	public void setUsuariocadastro(Usuario usuariocadastro) {
		this.usuariocadastro = usuariocadastro;
	}

	public Usuario getUsuarioalteracao() {
		return usuarioalteracao;
	}

	public void setUsuarioalteracao(Usuario usuarioalteracao) {
		this.usuarioalteracao = usuarioalteracao;
	}

	public Set<Cargo> getCargos() {
		return cargos;
	}

	public void setCargos(Set<Cargo> cargos) {
		this.cargos = cargos;
	}

	public Set<Profissao> getProfissoes() {
		return profissoes;
	}

	public void setProfissoes(Set<Profissao> profissoes) {
		this.profissoes = profissoes;
	}

	public String getProfissional() {
		return profissional;
	}

	public void setProfissional(String profissional) {
		this.profissional = profissional;
	}

	

}
