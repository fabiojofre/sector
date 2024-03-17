package com.jofre.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jofre.domain.Tipo;

public interface TipoRepository extends JpaRepository<Tipo, Long> {
	

}
