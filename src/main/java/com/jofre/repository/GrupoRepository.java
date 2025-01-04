package com.jofre.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jofre.domain.Grupo;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {

	@Query("select g from Grupo g where g.descricao like %:search%")
	Page<Grupo> findAllByNome(String search, Pageable pageable);
	
	@Query("select g.descricao from Grupo g where g.descricao like %:termo%")
	List<String>findGruposByTermo(String termo);

	@Query("select g from Grupo g where g.descricao IN :nomes")
	Set<Grupo> findByNomes(String[] nomes);

}
