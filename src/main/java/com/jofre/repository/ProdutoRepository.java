package com.jofre.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jofre.domain.Produto;
import com.jofre.repository.projection.HistoricoProdutos;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	
	@Query("select p from Produto p where p.grupo.id = :grupo ")
	List<Produto> findByGrupo(Long grupo);

	@Query("select p from Produto p where p.ativo = true ")
	Page<HistoricoProdutos> findAllProdutosAtivosPaginados(Pageable pageable);

	@Query("select p from Produto p  ")
	Page<HistoricoProdutos> findAllProdutosPaginados(Pageable pageable);

	@Query("update Produto p set p.estoque = p.estoque - :quantidade where p.id = :id ")
	void saidaDeEstoque(Double quantidade, Long id);
	
	@Query("update Produto p set p.estoque = p.estoque + :quantidade where p.id = :id ")
	void entradaDeEstoque(Double quantidade, Long id);

	@Query("update Produto p set p.estoque =  :quantidade where p.id = :id ")
	void acertaEstoque(Double quantidade, Long id);

	@Query("update Produto p set p.estoque = 0 where p.id = :id ")
	void zeraEstoque(Long id);

	@Query("update Produto p set p.precoCompra = :custo where p.id = :id ")
	void atualizaCusto(Double custo, Long id);
	
	
	
}
