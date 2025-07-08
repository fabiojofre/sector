package com.jofre.datatables;

public class DatatablesColunas {

	public static final String[] ESPECIALIDADES = {"id", "titulo"};

	public static final String[] ESPECIALISTAS = {"id", "nome", "crm", "dtInscricao", "especialidades"};
	
	public static final String[] AGENDAMENTOS = {"id", "pessoa.nome", "dataConsulta", "especialista.nome", "especialidade.titulo","finalizado"};

	public static final String[] USUARIOS = {"id", "email", "ativo", "perfis"};	
	//novo
	public static final String[] CONGREGACOES = {"id", "nome", "responsavel","area"};
	//novo
	public static final String[] CARGOS = {"id", "nome","estilo","sigla"};
	//novo
	public static final String[] PROFISSOES = {"id","nome"};
	//novo
	public static final String[] CONVERTIDOS = {"id","nome","origemConversao","telefone","dataConversao","endereco","congregacao.nome","pessoa.nome","pessoa.telefone","dataNascimento","dataConclusao","aulas[]"};
	
	public static final String[] ORIGENS = {"id", "nome"};
	
	public static final String[] ORIGEM_CONVERSAO = {"id", "nome","inativo"};
	
	public static final String[] PROFESSOR = {"id","nome","telefone","congregacao.nome","dataNascimento"};
		
	public static final String[] LIMITACOES = {"id", "nome"};
	
	public static final String[] GRAUS = {"id", "nome"};
	
	public static final String[] CONVITES = {"id","congregacao.nome","tipo.nome","dataEvento", "area","congregacoes[].congregacao.nome"};
	
	public static final String[] CONGREGADOS = {"id","nome","telefone","dataNascimento","congregacao.nome","cartaoMembro"};
	
	public static final String[] AULAS = {"id", "licao", "dataAula","professor.nome","aulas[].aula.nome"};
	
	public static final String[] PRODUTOS = {"id", "descricao", "grupo", "precoCompra","estoque","reservado"};
	
	public static final String[] GRUPOS = {"id", "descricao"};
	
	// tabelas não reais no banco de dados para relatórios
	public static final String[] CONVERTIDOCONGREGACAO = {"nome","quantidade"};
	public static final String[] CONVERTIDOCONGREGACAOAREA = {"nome","area","quantidade"};
	public static final String[] CONVERTIDOAREA = {"area","quantidade"};
}
