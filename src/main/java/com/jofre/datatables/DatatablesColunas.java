package com.jofre.datatables;

public class DatatablesColunas {

	public static final String[] ESPECIALIDADES = {"id", "titulo"};

	public static final String[] ESPECIALISTAS = {"id", "nome", "crm", "dtInscricao", "especialidades"};
	
	public static final String[] AGENDAMENTOS = {"id", "pessoa.nome", "dataConsulta", "especialista.nome", "especialidade.titulo","finalizado"};

	public static final String[] USUARIOS = {"id", "email", "ativo", "perfis"};	
	//novo
	public static final String[] CONGREGACOES = {"id", "nome", "responsavel","ePolo" ,"area","diaDoutrina","diaOracaoMocidade","diaDiscipulado","semanaCeia","ebdSabado","area"};
	//novo
	public static final String[] CARGOS = {"id", "nome","estilo"};
	//novo
	public static final String[] CONVERTIDOS = {"id","nome","convertido","inativo","obsConversao","telefone","endereco","congregacao.nome","pessoa.nome","pessoa.telefone","dataConversao"};
	
	public static final String[] DISCIPULADO = {"id","nome","obs_conversao","telefone","congregacao.nome", "aulas"};
	
	public static final String[] EVENTO_CAMPANHA = {"id","tipo_evento","data", "congregacao.nome","congregacoes"};
	
}
