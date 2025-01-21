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
import com.jofre.domain.OrigemConversao;
import com.jofre.repository.OrigemConversaoRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class OrigemConversaoService {

	@Autowired
	private OrigemConversaoRepository repository;
	
	@Autowired
	private Datatables datatables;

	@Transactional(readOnly = false)
	public void salvar(OrigemConversao origemConversao) {
		repository.save(origemConversao);		
	}
	
	@Transactional(readOnly = true)
	public Map<String, Object> buscarOrigens(HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.ORIGEM_CONVERSAO);
		Page<?> page = datatables.getSearch().isEmpty()
				? repository.findAll(datatables.getPageable())
				: repository.findAllByNome(datatables.getSearch(), datatables.getPageable());
		return datatables.getResponse(page);
	}
	
	@Transactional(readOnly = true)
	public OrigemConversao buscarPorId(Long id) {
		return repository.findById(id).get();
	}

	@Transactional(readOnly = false)
	public void remover(Long id) {
		repository.deleteById(id);
	}
	
	@Transactional(readOnly = true)
	public List<String> buscarOrigemByTermo(String termo) {
		return repository.findOrigensByTermo(termo);
	}
	
	@Transactional(readOnly = true)
	public Set<OrigemConversao> buscarPorTitulos(String[] nomes) {
		return repository.findByNomes(nomes);
	}
	
	@Transactional(readOnly = true)
	public List<OrigemConversao> buscarTodos() {
		return repository.findAll();
	} 
		
}
