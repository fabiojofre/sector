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
import com.jofre.domain.Congregacao;
import com.jofre.repository.CongregacaoRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class CongregacaoService {

	@Autowired
	private CongregacaoRepository repository;
	
	@Autowired
	private Datatables datatables;

	@Transactional(readOnly = false)
	public void salvar(Congregacao congregacao) {
		repository.save(congregacao);		
	}
	
	@Transactional(readOnly = true)
	public Map<String, Object> buscarCongregacoes(HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.CONGREGACOES);
		Page<?> page = datatables.getSearch().isEmpty()
				? repository.findAll(datatables.getPageable())
				: repository.findAllByNome(datatables.getSearch(), datatables.getPageable());
		return datatables.getResponse(page);
	}
	
	@Transactional(readOnly = true)
	public Congregacao buscarPorId(Long id) {
		return repository.findById(id).get();
	}

	@Transactional(readOnly = false)
	public void remover(Long id) {
		repository.deleteById(id);
	}
	
	@Transactional(readOnly = true)
	public List<String> buscarCongregacaoByTermo(String termo) {
		
		return repository.findCongregacoesByTermo(termo);
	}
	
	@Transactional(readOnly = true)
	public Set<Congregacao> buscarPorTitulos(String[] nomes) {
		return repository.findBynomes(nomes);
	}
	
	@Transactional(readOnly = true)
	public List<Congregacao> buscarTodos() {
		return repository.findAll();
	} 
	

	@Transactional(readOnly = true)
	public Set<Congregacao> buscarCongregacaoPorArea(Integer area) {
		
		return repository.findByCongregacaoPorArea(area);
	}
	
	@Transactional(readOnly = true)
	public Congregacao buscarCongregacaoPorNome(String nome) {
		
		return repository.findCongregacaoByNome(nome);
	}

	
	} 
	
//	@Transactional(readOnly = true)
//	public Map<String, Object> buscarEspecialidadesPorespecialista(Long id, HttpServletRequest request) {
//		datatables.setRequest(request);
//		datatables.setColunas(DatatablesColunas.ESPECIALIDADES);
//		Page<Especialidade> page = repository.findByIdMembro(id, datatables.getPageable()); 
//		return datatables.getResponse(page);
//	}
	
//}
