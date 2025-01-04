package com.jofre.repository.projection;

import java.time.LocalDate;

import com.jofre.domain.EstadoCivil;
import com.jofre.domain.Grau;
import com.jofre.domain.Pessoa;

public interface HistoricoProfessor {

	Long getId();
	
	String getNome();
	
	Long getTelefone();

	LocalDate getDataNascimento(); 
	
	Pessoa getPessoa();
	
	EstadoCivil getEstadoCivil();
	
	Grau getGrau();
}
