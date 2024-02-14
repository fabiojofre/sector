package com.jofre.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jofre.domain.Convertido;
import com.jofre.repository.projection.HistoricoConvertido;

public interface ConvertidoRepository extends JpaRepository<Convertido, Long>{


	@Query("select c from Convertido c where c.nome like %:search%")
	Page<Convertido> findAllByNome(String search, Pageable pageable);

	@Query("select c.nome from Convertido c where c.nome like %:termo%")
	List<String> findConvertidosByTermo(String termo);

	@Query("select c from Convertido c where c.congregacao.id = :id")
	Page<Convertido> findByCongregacaoId(Long id, Pageable pageable);

	@Query("select c.id as id, "
			+ "c.nome as nome,"
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
			+ "c.dataConversao as dataConversao,"
			+ "c.endereco as endereco,"
			+ "c.congregacao as congregacao,"
			+ "c.pessoa as pessoa "
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

	

	
	
}
