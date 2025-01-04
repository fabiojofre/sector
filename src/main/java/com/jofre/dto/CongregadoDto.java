package com.jofre.dto;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * dto para formulario ajax
 */
public class CongregadoDto implements Serializable {
	
	private String nome; 
	private String telefone;
	private LocalDate dataNascimento;
	private String congregacao;
	private String cartaoMembro;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public String getCongregacao() {
		return congregacao;
	}
	public void setCongregacao(String congregacao) {
		this.congregacao = congregacao;
	}
	public String getCartaoMembro() {
		return cartaoMembro;
	}
	public void setCartaoMembro(String cartaoMembro) {
		this.cartaoMembro = cartaoMembro;
	}
	
	
	
}
