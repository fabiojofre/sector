package com.jofre.domain;

public enum PerfilTipo {
	ADMIN(1, "ADMIN"), 
	ESPECIALISTA(2, "ESPECIALISTA"), 
	PESSOA(3, "PESSOA"),
	DISCIPULADO(4,"DISCIPULADO"),
	CAMPANHA(5,"CAMPANHA"),
	ALMOXARIFADO(6, "ALMOXARIFADO"),
	ASSISTENTE(7, "ASSISTENTE");
	
	private long cod;
	private String desc;

	private PerfilTipo(long cod, String desc) {
		this.cod = cod;
		this.desc = desc;
	}

	public long getCod() {
		return cod;
	}

	public String getDesc() {
		return desc;
	}
}
