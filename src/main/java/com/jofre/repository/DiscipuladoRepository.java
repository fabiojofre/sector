package com.jofre.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jofre.domain.Cargo;
import com.jofre.domain.Discipulado;

public interface DiscipuladoRepository extends JpaRepository<Discipulado, Long> {
	
	@Query("select d from Discipulado d where d.nome like :search%")
	Page<Discipulado> findAllByNome(String search, Pageable pageable);

	@Query("select d.nome from Discipulado d where d.nome like :termo%")
	List<String> findDiscipuladosByTermo(String termo);

	@Query("select d from Discipulado d where d.nome IN :nomes")
	Set<Discipulado> findBynomes(String[] nomes);

	@Query("select d from Discipulado d "
			+ "join d.aulas a "
			+ "where a.id = :id") 
	Page<Discipulado> findByIdAula(Long id, Pageable pageable);

}
