package com.jofre.repository.projection;

import java.time.LocalDate;
import java.util.List;

import com.jofre.domain.Congregacao;
import com.jofre.domain.Pessoa;
import com.jofre.domain.Tipo;

public interface HistoricoConvite {

	Long getId();
	LocalDate getDataEvento();
	Tipo getTipo();
	Integer getArea();
	Congregacao getCongregacao();
	List<Congregacao> getCongregacoes(); 
	Pessoa getPessoa();
	Boolean getAtivo();
	Boolean getLiberado();
}
