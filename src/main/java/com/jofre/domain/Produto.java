package com.jofre.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

//@SuppressWarnings("serial")
//@Entity
//@Table(name = "produtos", indexes = {@Index(name = "idx_produto_nome", columnList = "descricao")})
public class Produto extends AbstractEntity {
	
	@Column(name = "descricao", unique = true,  nullable = false)
	private String descricao;
	
	@Column(name = "preco_compra",nullable = false)
	private Double precoCompra;
	
	@Column(name = "estoque",nullable = false)
	private Double estoque;
	
	@Column(name = "reservado",nullable = false)
	private Double reservado;
	
	@Column(name = "ativo")
	private Boolean ativo;
	
	@Column(name = "controla_estoque")
	private Boolean controlaEstoque;
	
	private Unidade unidade;
}
