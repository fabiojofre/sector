package com.jofre.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jofre.domain.Convertido;
import com.jofre.repository.projection.HistoricoConvertido;
import com.jofre.repository.projection.HistoricoConvertidoArea;
import com.jofre.repository.projection.HistoricoConvertidoSetor;
import com.jofre.repository.projection.HistoricoConvertidoSetorArea;

public interface ConvertidoRepository extends JpaRepository<Convertido, Long>{


	@Query("select c from Convertido c where c.nome like %:search%")
	Page<Convertido> findAllByNome(String search, Pageable pageable);

	@Query("select c.nome from Convertido c where c.nome like %:termo%")
	List<String> findConvertidosByTermo(String termo);

	@Query("select c from Convertido c where c.congregacao.id = :id")
	Page<Convertido> findByCongregacaoId(Long id, Pageable pageable);

	@Query("select c.id as id, "
			+ "c.nome as nome,"
			+ "c.origemConversao as origemConversao,"
			+ "c.telefone as telefone, "
			+ "c.dataConversao as dataConversao,"
			+ "c.endereco as endereco,"
			+ "c.congregacao as congregacao,"
			+ "c.pessoa as pessoa "
			+ "from Convertido c "
			+ "where c.pessoa.usuario.email = :email "
			+ "and (c.matriculado = false or c.matriculado = null) "
			+ "and (c.concluinte = false or c.concluinte = null) "
			+ "and (c.inativo =  false or c.inativo = null) ")
	Page<HistoricoConvertido> findHistoricoConvertidoEmail(String email, Pageable pageable);

	
	@Query("select c.id as id, "
			+ "c.nome as nome,"
			+ "c.origemConversao as origemConversao,"
			+ "c.telefone as telefone, "
			+ "c.dataConversao as dataConversao,"
			+ "c.endereco as endereco,"
			+ "c.congregacao as congregacao,"
			+ "c.pessoa as pessoa "
			+ "from Convertido c "
			+ "where c.congregacao.id = :congregacao "
			+ "and (c.matriculado = false or c.matriculado = null) "
			+ "and (c.concluinte = false or c.concluinte = null) "
			+ "and (c.inativo =  false or c.inativo = null) ")
	Page<HistoricoConvertido> findHistoricoConvertidoCongregacao(Long congregacao, Pageable pageable); 
	
	
	@Query("select c.id as id, "
			+ "c.nome as nome,"
			+ "c.telefone as telefone, "
			+ "c.dataNascimento as dataNascimento "
			+ "from Convertido c "
			+ "where c.congregacao.id = :congregacao "
			+ "and c.matriculado = true "
			+ "and (c.concluinte = false or c.concluinte = null) "
			+ "and (c.inativo =  false or c.inativo = null) ")
	Page<HistoricoConvertido> findHistoricoMatriculadoCongregacao(Long congregacao, Pageable pageable);
	
	
	@Query("select c from Convertido c where c.pessoa.id = :pessoa")
	List<Convertido> findConvertidoByPessoa(Long pessoa);

	@Query("select c from Convertido c where c.id = :id AND c.pessoa.usuario.email like :email ")
	Optional<Convertido> findByIdAndPessoaEmail(Long id, String email);

	@Query("select c.id as id, "
			+ "c.nome as nome,"
			+ "c.telefone as telefone, "
			+ "c.dataConversao as dataConversao,"
			+ "c.endereco as endereco,"
			+ "c.congregacao as congregacao,"
			+ "c.pessoa as pessoa "
			+ "from Convertido c "
			+ "where (c.congregacao.id = :congregacao "
			+ "or c.pessoa.id = :pessoa) "
			+ "and (c.matriculado = false or c.matriculado = null) "
			+ "and (c.concluinte = false or c.concluinte = null) "
			+ "and (c.inativo =  false or c.inativo = null) ")
	Page<HistoricoConvertido> findHistoricoConvertidoPessoaCongregacao(Long congregacao, Long pessoa,
			Pageable pageable);
	
	
	@Query("select "
			+ "c.pessoa.congregacao.nome as nome, "
			+ "count(c.id) as quantidade "
			+ "from Convertido c "
			+ "where c.pessoa.area = :area "
			+ "and DATE_PART('week', c.dataConversao +1) =  DATE_PART('week', now()) " //semana
			+ "and DATE_PART('year', c.dataConversao +1) =  DATE_PART('year', now()) " // ano
			+ "and (c.matriculado = false or c.matriculado = null) "
			+ "group by 1 ")
	Page<HistoricoConvertidoArea> findHistoricoConvertidoAreaData(Integer area, Pageable pageable);

	
	@Query("select c.id as id, "
			+ "c.nome as nome, "
			+ "c.telefone as telefone, "
			+ "c.dataNascimento as dataNascimento,"
			+ "c.dataConclusao as dataConclusao "
			+ "from Convertido c "
			+ "where c.congregacao.id = :congregacao "
			+ "and c.matriculado = true "
			+ "and c.concluinte = true  "
			+ "and (c.inativo =  false or c.inativo = null) ")
	Page<HistoricoConvertido> findHistoricoConcluidoCongregacao(Long congregacao, Pageable pageable);

	
	@Query("select c from Convertido c "
			+ "where c.congregacao.id = :congregacao "
			+ "and matriculado = true "
			+ "and inativo = false "
			+ "and (concluinte = null or concluinte = false)")
	List<Convertido> findAllConvertidoMatriculadoPorCongregacao(Long congregacao);

	
	@Query("select "
			+ "c.pessoa.congregacao.nome as nome, "
			+ "c.pessoa.congregacao.area as area, "
			+ "count(c.id) as quantidade "
			+ "from Convertido c "
			+ "where DATE_PART('week', c.dataConversao +1) =  DATE_PART('week', now()) " //semana
			+ "and DATE_PART('year', c.dataConversao +1) =  DATE_PART('year', now()) " // ano
			+ "and (c.matriculado = false or c.matriculado = null) "
			+ "group by c.pessoa.congregacao.nome, c.pessoa.congregacao.area")
	Page<HistoricoConvertidoSetor> findHistoricoConvertidoSetorData(Pageable pageable);

		
	@Query("select "
			+ "c.pessoa.congregacao.area as area, "
			+ "count(c.id) as quantidade "
			+ "from Convertido c "
			+ "where DATE_PART('week', c.dataConversao +1) =  DATE_PART('week', now()) " //semana
			+ "and DATE_PART('year', c.dataConversao +1) =  DATE_PART('year', now()) " // ano
			+ "and (c.matriculado = false or c.matriculado = null) "
			+ "group by c.pessoa.congregacao.area")
	Page<HistoricoConvertidoSetorArea> findHistoricoConvertidoSetorAreaSemana(Pageable pageable);

	@Query("select c from Convertido c where c.convertido = true and c.dataConversao >= :data")
	List<Convertido> findByAllPorData(LocalDate data);

}
