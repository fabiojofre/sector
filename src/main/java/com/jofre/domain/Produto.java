package com.jofre.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "produtos", indexes = {@Index(name = "idx_produto_nome", columnList = "descricao")})
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
	
	@Column(name = "unidade",nullable = false)
	private String unidade;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getPrecoCompra() {
		return precoCompra;
	}

	public void setPrecoCompra(Double precoCompra) {
		this.precoCompra = precoCompra;
	}

	public Double getEstoque() {
		return estoque;
	}

	public void setEstoque(Double estoque) {
		this.estoque = estoque;
	}

	public Double getReservado() {
		return reservado;
	}

	public void setReservado(Double reservado) {
		this.reservado = reservado;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Boolean getControlaEstoque() {
		return controlaEstoque;
	}

	public void setControlaEstoque(Boolean controlaEstoque) {
		this.controlaEstoque = controlaEstoque;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}
	
	
}
