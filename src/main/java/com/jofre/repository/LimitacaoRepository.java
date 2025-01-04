package com.jofre.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jofre.domain.Limitacao;

public interface LimitacaoRepository extends JpaRepository<Limitacao, Long> {
	
	@Query("select l from Limitacao l where l.nome like %:search%")
	Page<Limitacao> findAllByNome(String search, Pageable pageable);

	@Query("select l.nome from Limitacao l where l.nome like %:termo%")
	List<String>findLimitacoesByTermo(String termo);

	@Query("select l from Limitacao l where l.nome IN :nomes")
	Set<Limitacao> findByNomes(String[] nomes);

}
