package com.jofre.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jofre.domain.Professor;
import com.jofre.repository.projection.HistoricoProfessor;

public interface ProfessorRepository extends JpaRepository<Professor, Long>{


	@Query("select c from Professor c where c.nome like %:search%")
	Page<Professor> findAllByNome(String search, Pageable pageable);

	@Query("select c.nome from Professor c where c.nome like %:termo%")
	List<String> findProfessorsByTermo(String termo);

	@Query("select c from Professor c where c.congregacao.id = :id")
	Page<Professor> findByCongregacaoId(Long id, Pageable pageable);


	@Query("select c.id as id, "
			+ "c.nome as nome,"
			+ "c.telefone as telefone "
			+ "from Professor c "
			+ "where (c.inativo =  false or c.inativo = null) ")
	Page<HistoricoProfessor> findByProfessor(Pageable pageable);
	
	@Query("select p.id as id, "
			+ "p.nome as nome,"
			+ "p.telefone as telefone "
			+ "from Professor p "
			+ "where p.congregacao.id = :congregacao "
			+ "and (p.inativo =  false or p.inativo = null) "
			+ "order by p.nome ")
	Page<HistoricoProfessor> findHistoricoProfessorCongregacao(Long congregacao, Pageable pageable); 
	

	
	@Query("select c from Professor c where c.pessoa.id = :pessoa")
	List<Professor> findProfessorByPessoa(Long pessoa);

	@Query("select c from Professor c where c.id = :id AND c.pessoa.usuario.email like :email ")
	Optional<Professor> findByIdAndPessoaEmail(Long id, String email);

	@Query("select p from Professor p where p.inativo  = false order by p.nome")
	List<Professor> findAllProfessor();

	@Query("select p from Professor p where p.congregacao.id  = :congregacao and p.inativo = false order by p.nome")
	List<Professor> findAllByCongregacao(Long congregacao);

		
}
