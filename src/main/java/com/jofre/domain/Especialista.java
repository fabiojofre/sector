package com.jofre.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("serial")
@Entity
@Table(name = "especialistas")
public class Especialista extends AbstractEntity {

	@Column(name = "nome", unique = true, nullable = false)
	private String nome;
	
	@Column(name = "crm", unique = true, nullable = false)
	private Integer crm;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "data_inscricao", nullable = false)
	private LocalDate dtInscricao;
	
	// evita recursividade quando o json de resposta for criado para a datatables.
	@JsonIgnore
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
			name = "especialistas_tem_especialidades",
			joinColumns = @JoinColumn(name = "id_especialista", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "id_especialidade", referencedColumnName = "id")
    )
	private Set<Especialidade> especialidades;
	
	// evita recursividade quando o json de resposta for criado para a datatables.
	@JsonIgnore
	@OneToMany(mappedBy = "especialista")
	private List<Agendamento> agendamentos;
	
	@OneToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	
	@Column(name = "e_domingo")
	private Boolean eDomingo;
	
	@Column(name = "e_segunda")
	private Boolean eSegunda;
	
	@Column(name = "e_terca")
	private Boolean eTerca;
	
	@Column(name = "e_quarta")
	private Boolean eQuarta;
	
	@Column(name = "e_quinta")
	private Boolean eQuinta;
	
	@Column(name = "e_sexta")
	private Boolean eSexta;
	
	@Column(name = "e_sabado")
	private Boolean eSabado;
	
	public Especialista() {
		super();
	}

	public Especialista(Long id) {
		super.setId(id);
	}

	public Especialista(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getCrm() {
		return crm;
	}

	public void setCrm(Integer crm) {
		this.crm = crm;
	}

	public LocalDate getDtInscricao() {
		return dtInscricao;
	}

	public void setDtInscricao(LocalDate dtInscricao) {
		this.dtInscricao = dtInscricao;
	}

	public Set<Especialidade> getEspecialidades() {
		return especialidades;
	}

	public void setEspecialidades(Set<Especialidade> especialidades) {
		this.especialidades = especialidades;
	}

	public List<Agendamento> getAgendamentos() {
		return agendamentos;
	}

	public void setAgendamentos(List<Agendamento> agendamentos) {
		this.agendamentos = agendamentos;
	}	

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Boolean geteDomingo() {
		return eDomingo;
	}

	public void seteDomingo(Boolean eDomingo) {
		this.eDomingo = eDomingo;
	}

	public Boolean geteSegunda() {
		return eSegunda;
	}

	public void seteSegunda(Boolean eSegunda) {
		this.eSegunda = eSegunda;
	}

	public Boolean geteTerca() {
		return eTerca;
	}

	public void seteTerca(Boolean eTerca) {
		this.eTerca = eTerca;
	}

	public Boolean geteQuarta() {
		return eQuarta;
	}

	public void seteQuarta(Boolean eQuarta) {
		this.eQuarta = eQuarta;
	}

	public Boolean geteQuinta() {
		return eQuinta;
	}

	public void seteQuinta(Boolean eQuinta) {
		this.eQuinta = eQuinta;
	}

	public Boolean geteSexta() {
		return eSexta;
	}

	public void seteSexta(Boolean eSexta) {
		this.eSexta = eSexta;
	}

	public Boolean geteSabado() {
		return eSabado;
	}

	public void seteSabado(Boolean eSabado) {
		this.eSabado = eSabado;
	}

	
}
