package com.jofre.datatables;

public class DatatablesColunas {

	public static final String[] ESPECIALIDADES = {"id", "titulo"};

	public static final String[] ESPECIALISTAS = {"id", "nome", "crm", "dtInscricao", "especialidades"};
	
	public static final String[] AGENDAMENTOS = {"id", "pessoa.nome", "dataConsulta", "especialista.nome", "especialidade.titulo"};

	public static final String[] USUARIOS = {"id", "email", "ativo", "perfis"};	
}
