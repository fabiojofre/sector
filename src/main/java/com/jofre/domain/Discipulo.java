package com.jofre.domain;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "discipulo")
public class Discipulo extends AbstractEntity{
	
	@Column(name = "nome", nullable = false)
	private String nome;
	
	@Column(name = "telefone", nullable = false, length = 11)
	private String telefone;
	
	@Column(name = "endereco", nullable = false)
	private String endereco;
	
	@Column(name = "observacao")
	private String observacao;
	
	@Column(name = "data_aceite")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dtAceite;

	@Column(name = "data_nascimento")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dtNascimento;
	
	@Column(name = "data_cadastro",nullable = false)
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dtCadastro;
	
	@Column(name = "data_conclusao")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dtConclusao;
	
	@ManyToOne
	@JoinColumn(name = "id_congregacao",nullable = false)
	private Congregacao congregacao;
	
	@ManyToOne
	@JoinColumn(name = "id_pessoa", nullable = false)
	private Pessoa pessoa;
	
	@Column(name = "e_da_uniao")
	private boolean eDaUniao;
	
	@Column(name = "e_inativo")
	private boolean eInativo;
	
	@Column(name = "e_concluinte")
	private boolean eConcluinte;
	
	@Column(name = "e_inapto_ao_batismo")
	private boolean eInaptoAoBatismo;
	
	@Column(name = "e_campanha")
	private boolean eCamapanha;
	
	@Column(name = "e_matriculado")
	private boolean eMatriculado;
}
