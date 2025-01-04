package com.jofre.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jofre.domain.Grau;

public interface GrauRepository extends JpaRepository<Grau, Long> {
	
	@Query("select g from Grau g where g.nome like %:search%")
	Page<Grau> findAllByNome(String search, Pageable pageable);

	@Query("select g.nome from Grau g where g.nome like %:termo%")
	List<String>findGrausByTermo(String termo);

	@Query("select g from Grau g where g.nome IN :nomes")
	Set<Grau> findByNomes(String[] nomes);

}
