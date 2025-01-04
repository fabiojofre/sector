package com.jofre.repository.projection;

import java.time.LocalDate;
import java.util.List;

import com.jofre.domain.Cargo;
import com.jofre.domain.Congregacao;
import com.jofre.domain.EstadoCivil;
import com.jofre.domain.Grau;
import com.jofre.domain.Limitacao;
import com.jofre.domain.Profissao;

public interface HistoricoCongregado {
	
	Long getId();
	
	String getNome();
	
	Long getTelefone();
	
	LocalDate getDataNascimento();
	
	Congregacao getCongregacao();
	
	String getEndereco();
	
	Long getCartaoMembro();
	
	Limitacao getLimitacao();
	
	Grau getGrau();
	
	EstadoCivil getEstadoCivil();
	
	List<Cargo>getCargos();
	
	List<Profissao> getProfissoes();

}
