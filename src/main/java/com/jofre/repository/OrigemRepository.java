package com.jofre.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jofre.domain.Origem;

public interface OrigemRepository extends JpaRepository<Origem, Long> {
	
	@Query("select p from Origem p where p.nome like %:search%")
	Page<Origem> findAllByNome(String search, Pageable pageable);

	@Query("select p.nome from Origem p where p.nome like %:termo%")
	List<String>findOrigensByTermo(String termo);

	@Query("select p from Origem p where p.nome IN :nomes")
	Set<Origem> findByNomes(String[] nomes);

}
