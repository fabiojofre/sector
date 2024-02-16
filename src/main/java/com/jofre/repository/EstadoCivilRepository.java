package com.jofre.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jofre.domain.EstadoCivil;

public interface EstadoCivilRepository extends JpaRepository<EstadoCivil, Long> {
	

}
