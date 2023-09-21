package com.jofre.repository.projection;

import com.jofre.domain.Especialidade;
import com.jofre.domain.Especialista;
import com.jofre.domain.Pessoa;

public interface HistoricoPessoa {

	Long getId();
	
	Pessoa getPessoa();
	
	String getDataConsulta();
	
	Especialista getEspecialista();
	
	Especialidade getEspecialidade();
}
