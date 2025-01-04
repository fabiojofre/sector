package com.jofre.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jofre.domain.Congregacao;

public interface CongregacaoRepository extends JpaRepository<Congregacao, Long> {
	
	@Query("select c from Congregacao c where c.nome like :search%")
	Page<Congregacao> findAllByNome(String search, Pageable pageable);

	@Query("select c.nome from Congregacao c where c.nome like %:termo%")
	List<String> findCongregacoesByTermo(String termo);

	@Query("select c from Congregacao c where c.nome IN :nomes")
	Set<Congregacao> findBynomes(String[] nomes);

	@Query("select c from Congregacao c "
			+ "where area = :area "
			+ "order by nome") 
	Set<Congregacao> findByCongregacaoPorArea(Integer area);

	@Query("select c from Congregacao c where c.nome = :nome")
	Congregacao findCongregacaoByNome(String nome);

	
	// busca congregacoes com restiçes de ids e areas 
	@Query("select c from Congregacao c "
			+ "where c.id not in (:congregacoes) or "
			+ "c.area not in (:areas)")
	List<Congregacao>findByConviteCongregacaoDisponivel(List<Integer> areas, Set<Long> congregacoes );
	
	
	@Query("select c from Congregacao c order by c.nome")
	List<Congregacao>findCongregacaoOrdem();
	
}
