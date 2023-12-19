package com.jofre.domain;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

//@SuppressWarnings("serial")
//@Entity
//@Table(name = "covertidos", indexes = {@Index(name = "idx_congregacao_nome", columnList = "nome")})
public class Convertido extends AbstractEntity{
	
	@Column(name = "nome", unique = true,  nullable = false)
	private String nome;
	
	@Column(name = "convertido")
	private Boolean convertido;
	
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
	
	private Congregacao congregacao;
	
	@Column(name = "estado_civil")
	private String estadoCivil;
	
	@Column(name = "data_nascimento")
	private Date dataNascimento;
	
	@Column(name = "data_conversao")
	private Date dataConversao;
	
	@Column(name = "data_discipulado")
	private Date dataDiscipulado;
	
	@Column(name = "Data_conclusao")
	private Date DataConclusao;
	
	@Column(name = "obs_discipulado")
	private String obsDiscipulado;
	
	private List<Integer>aulas;
	
}
