package com.jofre.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jofre.domain.Agendamento;
import com.jofre.domain.Horario;
import com.jofre.repository.projection.HistoricoPessoa;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long>{

	@Query("select h "
			+ "from Horario h "
			+ "where not exists("
				+ "select a.horario.id "
					+ "from Agendamento a "
					+ "where "
						+ "a.especialista.id = :id and "
						+ "a.dataConsulta = :data and "
						+ "a.horario.id = h.id "
			+ ") "
			+ "order by h.horaMinuto asc")
	List<Horario> findByespecialistaIdAndDataNotHorarioAgendado(Long id, LocalDate data);

	@Query("select a.id as id,"
				+ "a.pessoa as pessoa,"
				+ "CONCAT(a.dataConsulta, ' ', a.horario.horaMinuto) as dataConsulta,"
				+ "a.especialista as especialista,"
				+ "a.especialidade as especialidade "
			+ "from Agendamento a "
			+ "where a.pessoa.usuario.email like :email")
	Page<HistoricoPessoa> findHistoricoByPessoaEmail(String email, Pageable pageable);

	@Query("select a.id as id,"
			+ "a.pessoa as pessoa,"
			+ "CONCAT(a.dataConsulta, ' ', a.horario.horaMinuto) as dataConsulta,"
			+ "a.especialista as especialista,"
			+ "a.especialidade as especialidade "
		+ "from Agendamento a "
		+ "where a.especialista.usuario.email like :email "
		+ "and  (a.finalizado = false "
		+ "or a.finalizado is null)")	
	Page<HistoricoPessoa> findHistoricoByespecialistaEmail(String email, Pageable pageable);

	@Query("select a from Agendamento a "
			+ "where "
			+ "	(a.id = :id AND a.pessoa.usuario.email like :email) "
			+ " OR "
			+ " (a.id = :id AND a.especialista.usuario.email like :email)")
	Optional<Agendamento> findByIdAndPessoaOrespecialistaEmail(Long id, String email);

}
