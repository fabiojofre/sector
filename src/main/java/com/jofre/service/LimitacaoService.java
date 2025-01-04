package com.jofre.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jofre.datatables.Datatables;
import com.jofre.datatables.DatatablesColunas;
import com.jofre.domain.Limitacao;
import com.jofre.repository.LimitacaoRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class LimitacaoService {

	@Autowired
	private LimitacaoRepository repository;
	
	@Autowired
	private Datatables datatables;

	@Transactional(readOnly = false)
	public void salvar(Limitacao limitacao) {
		repository.save(limitacao);		
	}
	
	@Transactional(readOnly = true)
	public Map<String, Object> buscarLimitacoes(HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.LIMITACOES);
		Page<?> page = datatables.getSearch().isEmpty()
				? repository.findAll(datatables.getPageable())
				: repository.findAllByNome(datatables.getSearch(), datatables.getPageable());
		return datatables.getResponse(page);
	}
	
	@Transactional(readOnly = true)
	public Limitacao buscarPorId(Long id) {
		return repository.findById(id).get();
	}

	@Transactional(readOnly = false)
	public void remover(Long id) {
		repository.deleteById(id);
	}
	
	@Transactional(readOnly = true)
	public List<String> buscarLimitacaoByTermo(String termo) {
		return repository.findLimitacoesByTermo(termo);
	}
	
	@Transactional(readOnly = true)
	public Set<Limitacao> buscarPorTitulos(String[] nomes) {
		return repository.findByNomes(nomes);
	}
	
	@Transactional(readOnly = true)
	public List<Limitacao> buscarTodos() {
		return repository.findAll();
	} 
		
}
