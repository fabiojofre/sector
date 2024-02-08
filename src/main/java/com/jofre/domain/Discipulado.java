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
@Table(name = "discipulados")
public class Discipulado extends AbstractEntity{
	
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
	
	@Column(name = "matriculado")
	private Boolean matriculado;
	
	@Column(name = "concluido")
	private Boolean concluido;
	
	@ManyToOne
	@JoinColumn(name= "id_congregacao")
	private Congregacao congregacao;
	
	@ManyToOne
	@JoinColumn(name= "id_usuario")
	private Usuario usuario_cadastro;
	
	@Column(name = "estado_civil")
	private String estadoCivil;
	
	@Column(name = "data_nascimento")
	private LocalDate dataNascimento;
	
	@Column(name = "data_conversao")
	private LocalDate dataConversao;
	
	@Column(name = "data_discipulado")
	private LocalDate dataDiscipulado;
	
	@Column(name = "Data_conclusao")
	private LocalDate DataConclusao;
	
	@Column(name = "obs_discipulado")
	private String obsDiscipulado;
	
	// evita recursividade quando o json de resposta for criado para a datatables.
		@JsonIgnore
		@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
		@JoinTable(
				name = "discipulado_tem_aulas",
				joinColumns = @JoinColumn(name = "id_discipulado", referencedColumnName = "id"),
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

		public Boolean getMatriculado() {
			return matriculado;
		}

		public void setMatriculado(Boolean matriculado) {
			this.matriculado = matriculado;
		}

		public Boolean getConcluido() {
			return concluido;
		}

		public void setConcluido(Boolean concluido) {
			this.concluido = concluido;
		}

		public Congregacao getCongregacao() {
			return congregacao;
		}

		public void setCongregacao(Congregacao congregacao) {
			this.congregacao = congregacao;
		}

		public Usuario getUsuario_cadastro() {
			return usuario_cadastro;
		}

		public void setUsuario_cadastro(Usuario usuario_cadastro) {
			this.usuario_cadastro = usuario_cadastro;
		}

		public String getEstadoCivil() {
			return estadoCivil;
		}

		public void setEstadoCivil(String estadoCivil) {
			this.estadoCivil = estadoCivil;
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

		public LocalDate getDataDiscipulado() {
			return dataDiscipulado;
		}

		public void setDataDiscipulado(LocalDate dataDiscipulado) {
			this.dataDiscipulado = dataDiscipulado;
		}

		public LocalDate getDataConclusao() {
			return DataConclusao;
		}

		public void setDataConclusao(LocalDate dataConclusao) {
			DataConclusao = dataConclusao;
		}

		public String getObsDiscipulado() {
			return obsDiscipulado;
		}

		public void setObsDiscipulado(String obsDiscipulado) {
			this.obsDiscipulado = obsDiscipulado;
		}

		public Set<Aula> getAulas() {
			return aulas;
		}

		public void setAulas(Set<Aula> aulas) {
			this.aulas = aulas;
		}
	
		
		
}
