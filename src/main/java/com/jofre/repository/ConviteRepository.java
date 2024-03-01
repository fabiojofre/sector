package com.jofre.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jofre.domain.Convite;

public interface ConviteRepository extends JpaRepository<Convite, Long> {

	// retorna as congregacoes que já foram convidadas mais de 1 vez pra festividade
	@Query("select cs.id from Convite c "
			+ "join c.congregacoes cs "
			+ "where c.dataEvento between :data -15 and :data +15 "
			+ "GROUP BY cs.id "
			+ "HAVING Count(*) > 1")
	List<Integer>findByCongregacoesRepetidas(LocalDate data);
	
	
	// retorna as ateas em que existem festividades
	@Query("select distinct c.area from Convite c "
			+ "where c.dataEvento = :data")
	List<Integer>findByAreasFestividades(LocalDate data);
	
	
	//retorna congregações cujos dias de cultos impedem de sair
	@Query("select c.id from Congregacao c "
			+ "where c.diaDoutrina = :diaSemana or "
			+ "c.diaOracaoMocidade = :diaSemana or "
			+ "c.diaEbd = :diaSemana ")
	List<Integer>findByCongregacesComDiasImportantes(Integer diaSemana);
	
}
