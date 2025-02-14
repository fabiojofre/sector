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
import com.jofre.domain.Grupo;
import com.jofre.repository.GrupoRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class GrupoService {

	@Autowired
	private GrupoRepository repository;
	
	@Autowired
	private Datatables datatables;

	@Transactional(readOnly = false)
	public void salvar(Grupo grupo) {
		repository.save(grupo);		
	}
	
	@Transactional(readOnly = true)
	public Map<String, Object> buscarGrupos(HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.GRUPOS);
		Page<?> page = datatables.getSearch().isEmpty()
				? repository.findAll(datatables.getPageable())
				: repository.findAllByNome(datatables.getSearch(), datatables.getPageable());
		return datatables.getResponse(page);
	}
	
	@Transactional(readOnly = true)
	public Grupo buscarPorId(Long id) {
		return repository.findById(id).get();
	}

	@Transactional(readOnly = false)
	public void remover(Long id) {
		repository.deleteById(id);
	}
	
	@Transactional(readOnly = true)
	public List<String> buscarGrupoByTermo(String termo) {
		
		return repository.findGruposByTermo(termo);
	}
	
	@Transactional(readOnly = true)
	public Set<Grupo> buscarPorTitulos(String[] nomes) {
		return repository.findByNomes(nomes);
	}
	
	@Transactional(readOnly = true)
	public List<Grupo> buscarTodos() {
		return repository.findAll();
	} 
	
//	@Transactional(readOnly = true)
//	public Map<String, Object> buscarEspecialidadesPorespecialista(Long id, HttpServletRequest request) {
//		datatables.setRequest(request);
//		datatables.setColunas(DatatablesColunas.ESPECIALIDADES);
//		Page<Especialidade> page = repository.findByIdMembro(id, datatables.getPageable()); 
//		return datatables.getResponse(page);
//	}
	
}
