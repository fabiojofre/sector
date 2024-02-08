package com.jofre.dto;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * dto para formulario ajax
 */
public class ConvertidoDto implements Serializable {
	
	private String nome; 
	private Boolean convertido;
	private Boolean inativo;
	private String obsConversao;
	private String telefone;
	private String endereco;
	private LocalDate dataConversao;
	private LocalDate dataNascimento;
	private String usuario;
	private String congregacao;
	
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
	public String getCongregacao() {
		return congregacao;
	}
	public void setCongregacao(String congregacao) {
		this.congregacao = congregacao;
	}
	public LocalDate getDataConversao() {
		return dataConversao;
	}
	public void setDataConversao(LocalDate dataConversao) {
		this.dataConversao = dataConversao;
	}
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
}
