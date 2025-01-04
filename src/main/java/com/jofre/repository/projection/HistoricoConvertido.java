package com.jofre.repository.projection;

import java.time.LocalDate;
import java.util.List;

import com.jofre.domain.Aula;
import com.jofre.domain.Congregacao;
import com.jofre.domain.EstadoCivil;
import com.jofre.domain.Pessoa;

public interface HistoricoConvertido {

	Long getId();
	
	String getNome();
	
	Long getTelefone();
	

	LocalDate getDataNascimento(); 
	
	String getEndereco();
	
	LocalDate getDataConversao();
	
	
	Congregacao getCongregacao();
	
	Pessoa getPessoa();
	
	EstadoCivil getEstadoCivil();
	
	LocalDate getDataConclusao();
	
	List<Aula>getAulas();
}
