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
import com.jofre.domain.Grau;
import com.jofre.repository.GrauRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class GrauService {

	@Autowired
	private GrauRepository repository;
	
	@Autowired
	private Datatables datatables;

	@Transactional(readOnly = false)
	public void salvar(Grau grau) {
		repository.save(grau);				
	}
	
	@Transactional(readOnly = true)
	public Map<String, Object> buscarGraus(HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.GRAUS);
		Page<?> page = datatables.getSearch().isEmpty()
				? repository.findAll(datatables.getPageable())
				: repository.findAllByNome(datatables.getSearch(), datatables.getPageable());
		return datatables.getResponse(page);
	}
	
	@Transactional(readOnly = true)
	public Grau buscarPorId(Long id) {
		return repository.findById(id).get();
	}

	@Transactional(readOnly = false)
	public void remover(Long id) {
		repository.deleteById(id);
	}
	
	@Transactional(readOnly = true)
	public List<String> buscarGrauByTermo(String termo) {
		return repository.findGrausByTermo(termo);
	}
	
	@Transactional(readOnly = true)
	public Set<Grau> buscarPorTitulos(String[] nomes) {
		return repository.findByNomes(nomes);
	}
	
	@Transactional(readOnly = true)
	public List<Grau> buscarTodos() {
		return repository.findAll();
	} 
		
}
