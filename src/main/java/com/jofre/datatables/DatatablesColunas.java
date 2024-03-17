package com.jofre.datatables;

public class DatatablesColunas {

	public static final String[] ESPECIALIDADES = {"id", "titulo"};

	public static final String[] ESPECIALISTAS = {"id", "nome", "crm", "dtInscricao", "especialidades"};
	
	public static final String[] AGENDAMENTOS = {"id", "pessoa.nome", "dataConsulta", "especialista.nome", "especialidade.titulo","finalizado"};

	public static final String[] USUARIOS = {"id", "email", "ativo", "perfis"};	
	//novo
	public static final String[] CONGREGACOES = {"id", "nome", "responsavel","area"};
	//novo
	public static final String[] CARGOS = {"id", "nome","estilo"};
	//novo
	public static final String[] CONVERTIDOS = {"id","nome","telefone","dataConversao","endereco","congregacao.nome","pessoa.nome","pessoa.telefone","ciclo.nome","dataNascimento","dataConclusao"};
	
	public static final String[] DISCIPULADO = {"id","nome","obs_conversao","telefone","congregacao.nome", "aulas"};
	
	public static final String[] CONVITES = {"id","congregacao.nome","tipo.nome","dataEvento", "area","congregacoes[].congregacao.nome"};
	
}
