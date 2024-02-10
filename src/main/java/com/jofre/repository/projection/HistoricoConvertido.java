package com.jofre.repository.projection;

import java.time.LocalDate;

import com.jofre.domain.Congregacao;
import com.jofre.domain.Pessoa;

public interface HistoricoConvertido {

	Long getId();
	
	String getNome();
	
	String getTelefone();
	
	String getEndereco();
	
	LocalDate getDataConversao();
	
	Congregacao getCongregacao();
	
	Pessoa getPessoa();
	
}
