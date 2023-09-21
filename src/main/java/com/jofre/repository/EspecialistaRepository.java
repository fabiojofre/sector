package com.jofre.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jofre.domain.Especialista;

public interface EspecialistaRepository extends JpaRepository<Especialista, Long>{

	@Query("select m from Especialista m where m.usuario.id = :id")
	Optional<Especialista> findByUsuarioId(Long id);

	@Query("select m from Especialista m where m.usuario.email like :email")
	Optional<Especialista> findByUsuarioEmail(String email);

	@Query("select distinct m from Especialista m "
			+ "join m.especialidades e "
			+ "where e.titulo like :titulo "
			+ "and m.usuario.ativo = true")
	List<Especialista> findByespecialistasPorEspecialidade(String titulo);

	@Query("select m.id "
			+ "from Especialista m "
			+ "join m.especialidades e "
			+ "join m.agendamentos a "
			+ "where "
			+ "a.especialidade.id = :idEsp AND a.especialista.id = :idMed")
	Optional<Long> hasEspecialidadeAgendada(Long idMed, Long idEsp);
}
