package com.jofre.repository.projection;

import com.jofre.domain.Grupo;

public interface HistoricoProdutos {

	String getDescricao();
	
	Double getPrecoCompra();
	
	Double getEstoque();
	
	Double getReservado();

	String getUnidade();
	
	Grupo getGrupo();

}
