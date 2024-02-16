package com.jofre.repository.projection;

import java.time.LocalDate;

import com.jofre.domain.Ciclo;
import com.jofre.domain.Congregacao;
import com.jofre.domain.EstadoCivil;
import com.jofre.domain.Pessoa;

public interface HistoricoConvertido {

	Long getId();
	
	String getNome();
	
	String getTelefone();
	
	String getEndereco();
	
	LocalDate getDataConversao();
	
	LocalDate getDataNascimento(); 
	
	Congregacao getCongregacao();
	
	Pessoa getPessoa();
	
	EstadoCivil getEstadoCivil();
	
	Ciclo getCiclo();
	
}
