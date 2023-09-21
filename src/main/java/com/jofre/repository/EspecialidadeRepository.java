package com.jofre.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jofre.domain.Especialidade;

public interface EspecialidadeRepository extends JpaRepository<Especialidade, Long>{

	@Query("select e from Especialidade e where e.titulo like :search%")
	Page<Especialidade> findAllByTitulo(String search, Pageable pageable);

	@Query("select e.titulo from Especialidade e where e.titulo like :termo%")
	List<String> findEspecialidadesByTermo(String termo);

	@Query("select e from Especialidade e where e.titulo IN :titulos")
	Set<Especialidade> findByTitulos(String[] titulos);

	@Query("select e from Especialidade e "
			+ "join e.especialistas m "
			+ "where m.id = :id") 
	Page<Especialidade> findByIdespecialista(Long id, Pageable pageable);

	
}
