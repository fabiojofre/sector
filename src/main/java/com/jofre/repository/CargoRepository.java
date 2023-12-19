package com.jofre.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jofre.domain.Cargo;

public interface CargoRepository extends JpaRepository<Cargo, Long> {
	
	@Query("select c from Cargo c where c.nome like :search%")
	Page<Cargo> findAllByNome(String search, Pageable pageable);

	@Query("select c.nome from Cargo c where c.nome like :termo%")
	List<String> findCargosByTermo(String termo);

	@Query("select c from Cargo c where c.nome IN :nomes")
	Set<Cargo> findBynomes(String[] nomes);

//	@Query("select c from Cargo c "
//			+ "join c.membros m "
//			+ "where m.id = :id") 
//	Page<Cargo> findByIdMembro(Long id, Pageable pageable);

}
