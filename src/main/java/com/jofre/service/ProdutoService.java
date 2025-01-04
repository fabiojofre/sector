package com.jofre.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jofre.datatables.Datatables;
import com.jofre.datatables.DatatablesColunas;
import com.jofre.domain.Produto;
import com.jofre.exception.AcessoNegadoException;
import com.jofre.repository.ProdutoRepository;
import com.jofre.repository.projection.HistoricoProdutos;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ProdutoService {

	@Autowired
	ProdutoRepository repository;
	
	@Autowired
	private Datatables datatables;
	
	
	@Transactional(readOnly = false)
	public void salvar(Produto produto) {
		repository.save(produto);
	}
	
	@Transactional(readOnly = true)
	public Produto buscarPorIdEusuario(Long Id, String email) {
		
		return repository.findById(Id)
				.orElseThrow(() -> new AcessoNegadoException("Acesso Negado para o Usuario"+ email));
	}
	
	@Transactional(readOnly = false)
	public void editar(Produto produto, String email) {
		Produto prod = buscarPorIdEusuario(produto.getId(), email);
		
		prod.setAtivo(produto.getAtivo());
		prod.setDescricao(produto.getDescricao());
//		prod.setEstoque(produto.getEstoque());   altera somente nas entradas 
//		prod.setPrecoCompra(produto.getPrecoCompra());	e saídas de produtos
		prod.setUnidade(produto.getUnidade());
		prod.setGrupo(produto.getGrupo());
	
	}
	
	public Map<String, Object>buscarProdutosPaginados(HttpServletRequest request){
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.PRODUTOS);
		Page<HistoricoProdutos>page = repository.findAllProdutosPaginados(datatables.getPageable());
		return datatables.getResponse(page);
	}
	
	
	@Transactional(readOnly = true)
	List <Produto> buscarPorGrupo(Long grupo){
		return repository.findByGrupo(grupo);
	}
	
	@Transactional(readOnly = false)
	public void excluir(Long id) {
		repository.deleteById(id);
	}
	
	@Transactional(readOnly = false)
	public void movimentaEstoque(int tipo, Double quantidade, Long id) {
		if(tipo == 0) {
			repository.saidaDeEstoque(quantidade,id);
		}else if(tipo == 1) {
			repository.entradaDeEstoque(quantidade,id);
		}else if(tipo == 2) {
			repository.acertaEstoque(quantidade,id);
		}else
			repository.zeraEstoque(id);
	}
	
	public void atualizaCusto(int tipo, Double custo, Long id ) {
		if(tipo == 0) {
			repository.atualizaCusto(custo,id);
		}
	}
	
}
