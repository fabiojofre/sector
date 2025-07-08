package com.jofre.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jofre.domain.CargoEc;

public interface CargoEcRepository extends JpaRepository<CargoEc, Long> {
	
	@Query("select c from CargoEc c where c.nome like :search%")
	Page<CargoEc> findAllByNome(String search, Pageable pageable);

	@Query("select c.nome from CargoEc c where c.nome like :termo%")
	List<String> findCargosByTermo(String termo);

	@Query("select c from CargoEc c where c.nome IN :nomes")
	Set<CargoEc> findBynomes(String[] nomes);

}
