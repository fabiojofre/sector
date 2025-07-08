package com.jofre.domain;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

@SuppressWarnings("serial")
@Entity
@Table(name = "obreiros")
public class Obreiro extends AbstractEntity{

	@Column(nullable =  false, length = 60)
	private String nome;
	

	@Column(nullable =  false)
	private Long cartaoMembro;
	
	private Boolean bloqueado;
	private Boolean trabalhoIntermitente;
	private Boolean trabalhoNoturno;
	
	private String diasDisponivel;

	private Boolean eDomingo;
	private Boolean eSegunda;
	private Boolean eTerca;
	private Boolean eQuarta;
	private Boolean eQuinta;
	private Boolean eSexta;
	private Boolean eSabado;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(columnDefinition = "DATE")
	private LocalDate dataUltimaEscala;
	

	@DateTimeFormat(iso = ISO.DATE)
	@Column(columnDefinition = "DATE")
	private LocalDate dataNascimento;
	
	private Integer escalaIntermitente;
	
	@Column(length = 10)
	private String disponiblidade;
	
	private String whatsapp;
	
	

	@ManyToOne
	@JoinColumn(name= "id_congregacao")
	private Congregacao congregacao;
	

	@ManyToOne
	@JoinColumn(name= "id_cargo_ec")
	private CargoEc cargoEc;
	
	
	@Size( max = 4)
	private String circulo;
	
	@Size( max = 50)
	private String acesso;

	public String getNome() {
		return nome;
	}

	public Long getCartaoMembro() {
		return cartaoMembro;
	}

	public Boolean getBloqueado() {
		return bloqueado;
	}

	public Boolean getTrabalhoIntermitente() {
		return trabalhoIntermitente;
	}

	public Boolean getTrabalhoNoturno() {
		return trabalhoNoturno;
	}

	public String getDiasDisponivel() {
		return diasDisponivel;
	}

	public Boolean geteDomingo() {
		return eDomingo;
	}

	public Boolean geteSegunda() {
		return eSegunda;
	}

	public Boolean geteTerca() {
		return eTerca;
	}

	public Boolean geteQuarta() {
		return eQuarta;
	}

	public Boolean geteQuinta() {
		return eQuinta;
	}

	public Boolean geteSexta() {
		return eSexta;
	}

	public Boolean geteSabado() {
		return eSabado;
	}

	public LocalDate getDataUltimaEscala() {
		return dataUltimaEscala;
	}

	public Integer getEscalaIntermitente() {
		return escalaIntermitente;
	}

	public String getDisponiblidade() {
		return disponiblidade;
	}

	public String getWhatsapp() {
		return whatsapp;
	}

	public Congregacao getCongregacao() {
		return congregacao;
	}



	public CargoEc getCargoEc() {
		return cargoEc;
	}

	public void setCargoEc(CargoEc cargoEc) {
		this.cargoEc = cargoEc;
	}

	public String getCirculo() {
		return circulo;
	}

	public String getAcesso() {
		return acesso;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setCartaoMembro(Long cartaoMembro) {
		this.cartaoMembro = cartaoMembro;
	}

	public void setBloqueado(Boolean bloqueado) {
		this.bloqueado = bloqueado;
	}

	public void setTrabalhoIntermitente(Boolean trabalhoIntermitente) {
		this.trabalhoIntermitente = trabalhoIntermitente;
	}

	public void setTrabalhoNoturno(Boolean trabalhoNoturno) {
		this.trabalhoNoturno = trabalhoNoturno;
	}

	public void setDiasDisponivel(String diasDisponivel) {
		this.diasDisponivel = diasDisponivel;
	}

	public void seteDomingo(Boolean eDomingo) {
		this.eDomingo = eDomingo;
	}

	public void seteSegunda(Boolean eSegunda) {
		this.eSegunda = eSegunda;
	}

	public void seteTerca(Boolean eTerca) {
		this.eTerca = eTerca;
	}

	public void seteQuarta(Boolean eQuarta) {
		this.eQuarta = eQuarta;
	}

	public void seteQuinta(Boolean eQuinta) {
		this.eQuinta = eQuinta;
	}

	public void seteSexta(Boolean eSexta) {
		this.eSexta = eSexta;
	}

	public void seteSabado(Boolean eSabado) {
		this.eSabado = eSabado;
	}

	public void setDataUltimaEscala(LocalDate dataUltimaEscala) {
		this.dataUltimaEscala = dataUltimaEscala;
	}

	public void setEscalaIntermitente(Integer escalaIntermitente) {
		this.escalaIntermitente = escalaIntermitente;
	}

	public void setDisponiblidade(String disponiblidade) {
		this.disponiblidade = disponiblidade;
	}

	public void setWhatsapp(String whatsapp) {
		this.whatsapp = whatsapp;
	}

	public void setCongregacao(Congregacao congregacao) {
		this.congregacao = congregacao;
	}


	public void setCirculo(String circulo) {
		this.circulo = circulo;
	}

	public void setAcesso(String acesso) {
		this.acesso = acesso;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	
	
}
