package com.jofre.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jofre.domain.OrigemConversao;

public interface OrigemConversaoRepository extends JpaRepository<OrigemConversao, Long> {
	
	@Query("select p from OrigemConversao p where (p.nome like %:search%) order by p.nome")
	Page<OrigemConversao> findAllByNome(String search, Pageable pageable);

	@Query("select p.nome from OrigemConversao p where p.nome like %:termo% order by p.nome")
	List<String>findOrigensByTermo(String termo);

	@Query("select p from OrigemConversao p where p.nome IN :nomes")
	Set<OrigemConversao> findByNomes(String[] nomes);

	@Query("select p from OrigemConversao p order by p.nome")
	List<OrigemConversao> findAllOrderBy();


}
