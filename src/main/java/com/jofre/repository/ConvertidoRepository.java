package com.jofre.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jofre.domain.Congregacao;
import com.jofre.domain.Convertido;
import com.jofre.domain.Especialista;

public interface ConvertidoRepository extends JpaRepository<Convertido, Long>{


	@Query("select c from Convertido c where c.nome like %:search%")
	Page<Convertido> findAllByNome(String search, Pageable pageable);

	@Query("select c.nome from Convertido c where c.nome like %:termo%")
	List<String> findConvertidosByTermo(String termo);

	@Query("select c from Convertido c where c.congregacao.id = :id")
	Page<Convertido> findByCongregacaoId(Long id, Pageable pageable); 
	
	
}
