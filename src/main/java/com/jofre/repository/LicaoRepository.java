package com.jofre.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jofre.domain.Licao;

public interface LicaoRepository extends JpaRepository<Licao, Long> {
	

}
