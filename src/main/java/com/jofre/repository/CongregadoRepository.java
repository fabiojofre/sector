package com.jofre.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jofre.domain.Congregado;
import com.jofre.repository.projection.HistoricoCongregado;

public interface CongregadoRepository extends JpaRepository<Congregado,Long>{
	
	@Query("select c from Congregado c where c.nome like %:search%")
	Page<Congregado> findAllByNome(String search, Pageable pageable);

	@Query("select c.nome from Congregado c where c.nome like %:termo%")
	List<String>findCongregadoByTermo(String termo);
	
	@Query("select c from Congregado c where c.congregacao.id = :id")
	Page<Congregado> findByCongregacaoId(Long id, Pageable pageable);
	
	@Query("select c from Congregado c where c.usuariocadastro.id = :usuariocadastro")
	List<Congregado> findCongregadoByUsuarioCadastro(Long usuariocadastro);
	
	@Query("select c from Congregado c where c.usuarioalteracao.id = :usuarioalteracao")
	List<Congregado> findCongregadoByUsuarioAlteracao(Long usuarioalteracao);
	
	@Query("select c from Congregado c where c.cartaoMembro = :cartaoMembro ")
	List<Congregado>findByCartao(Long cartaoMembro);
	
	
	@Query("select "
			+ "c.id as id, "
			+ "c.nome as nome, "
			+ "c.telefone as telefone, "
			+ "c.dataNascimento as dataNascimento, "
			+ "c.endereco as endereco, "
			+ "c.congregacao.nome as congregacao "
			+ "from Congregado c "
			+ "where c.congregacao.id = :congregacao ")
	Page<HistoricoCongregado> findHistoricoCongregadoCongregacao(Long congregacao, Pageable pageable);

	@Query("select "
			+ "c.id as id, "
			+ "c.nome as nome, "
			+ "c.telefone as telefone, "
			+ "c.dataNascimento as dataNascimento, "
			+ "c.congregacao as congregacao "
			+ "from Congregado c "
			+ " where c.congregacao.id = :congregacao ")
	Page<HistoricoCongregado> findAllCongregadoByCongregacao(Pageable pageable, Long congregacao);
	
	@Query("select c from Congregado c where c.cpf = :cpf ")
	List<Congregado> findByCPF(String cpf); 
	
	@Query("select "
			+ "c.id as id, "
			+ "c.nome as nome, "
			+ "c.telefone as telefone, "
			+ "c.dataNascimento as dataNascimento, "
			+ "c.congregacao as congregacao "
			+ " from Congregado c")
	Page<HistoricoCongregado> findByCongregado(Pageable pageable); 
}
