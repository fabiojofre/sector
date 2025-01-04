package com.jofre.repository.projection;

import java.time.LocalDate;
import java.util.Set;

import com.jofre.domain.Convertido;
import com.jofre.domain.Licao;
import com.jofre.domain.Professor;

public interface HistoricoAula {

	Long getId();
	
	Licao getLicao();
	
	LocalDate getDataAula();
	
	Professor getProfessor();
	
	Set<Convertido>getConvertidos();
}
