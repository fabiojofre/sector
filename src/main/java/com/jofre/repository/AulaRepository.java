package com.jofre.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jofre.domain.Aula;
import com.jofre.repository.projection.HistoricoAula;

public interface AulaRepository extends JpaRepository<Aula, Long> {

	@Query("select "
			+ "a "	
			+ "from Aula a "
			+ "join Convertido c"
			+ " where a.congregacao.id = :congregacao "
			+ "and c.matriculado = true "
			+ "and (c.concluinte = false or c.concluinte = null) "
			+ "and inativo = false "
			+ "order by a.id desc")
	Page<HistoricoAula> findAllAulaByCongregacao(Pageable pageable, Long congregacao);


	@Query("select a from Aula a "
			+ "join Convertido c "
			+ " where c.id = :aluno")
	Page<HistoricoAula> findAllAulasByAlunos(Pageable pageable, Long aluno);


	
	
}
