package com.jofre.datatables;

public class DatatablesColunas {

	public static final String[] ESPECIALIDADES = {"id", "titulo"};

	public static final String[] ESPECIALISTAS = {"id", "nome", "crm", "dtInscricao", "especialidades"};
	
	public static final String[] AGENDAMENTOS = {"id", "pessoa.nome", "dataConsulta", "especialista.nome", "especialidade.titulo","finalizado"};

	public static final String[] USUARIOS = {"id", "email", "ativo", "perfis"};	
	
	public static final String[] CONGREGACOES = {"id", "nome", "responsavel","ePolo" ,"area","diaDoutrina","diaOracaoMocidade","diaDiscipulado","semanaCeia","ebdSabado"};
	
//	public static final String[] CONVERTIDOS = {"id", "nome", "dataNascimento","dataConversao","usuarioConversao", "situacaoCivil", "endereco", "area", "congregacao", "telefone", "eUniao"};

	
}
