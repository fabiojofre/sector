package com.jofre.domain;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

//@SuppressWarnings("serial")
//@Entity
//@Table(name = "unidades")
public class Unidade extends AbstractEntity {
	
	@Column(name = "descricao", unique = true,  nullable = false)
	private String descricao;
	
	@Column(name = "sigla", unique = true,  nullable = false)
	private String sigla;
	
	@Column(name = "fracionar")
	private Boolean fracionar;

	private List<Produto>produtos;
}
