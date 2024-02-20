package com.jofre.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jofre.domain.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

	@Query("select p from Pessoa p where p.usuario.email like :email")
	Optional<Pessoa> findByUsuarioEmail(String email);
	
	
	@Query("select p from Pessoa p where p.usuario.email like :email")
	Pessoa findByUsuarioEmailValido(String email);
}
