package com.jofre.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jofre.domain.Profissao;

public interface ProfissaoRepository extends JpaRepository<Profissao, Long> {
	
	@Query("select p from Profissao p where p.nome like %:search%")
	Page<Profissao> findAllByNome(String search, Pageable pageable);

	@Query("select p.nome from Profissao p where p.nome like %:termo%")
	List<String>findProfissoesByTermo(String termo);

	@Query("select p from Profissao p where p.nome IN :nomes")
	Set<Profissao> findByNomes(String[] nomes);

}
