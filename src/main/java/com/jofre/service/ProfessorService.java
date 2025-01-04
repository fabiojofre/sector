package com.jofre.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jofre.datatables.Datatables;
import com.jofre.datatables.DatatablesColunas;
import com.jofre.domain.Professor;
import com.jofre.exception.AcessoNegadoException;
import com.jofre.repository.ProfessorRepository;
import com.jofre.repository.projection.HistoricoProfessor;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ProfessorService {

	@Autowired
	private ProfessorRepository repository;

	@Autowired
	private Datatables datatables;

	@Transactional(readOnly = false)
	public void salvar(Professor professor) {	
		professor.setInativo(false);
		professor.setDataCadastro(LocalDate.now());
		repository.save(professor);
	}

	@Transactional(readOnly = true)
	public Map<String, Object> buscarProfessores(HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.CONVERTIDOS);
		Page<?> page = datatables.getSearch().isEmpty() ? repository.findAll(datatables.getPageable())
				: repository.findAllByNome(datatables.getSearch(), datatables.getPageable());
		return datatables.getResponse(page);
	}

	@Transactional(readOnly = true)
	public Professor buscarPorId(Long id) {
		return repository.findById(id).get();
	}

	@Transactional(readOnly = true)
	public List<Professor> buscarTodos() {
		return repository.findAllProfessor();
	}
	@Transactional(readOnly = false)
	public void editar(Professor professor, String email) {
		Professor pr = buscarPorIdEUsuario(professor.getId(), email);
		pr.setNome(professor.getNome());
		pr.setInativo(professor.getInativo());
		pr.setTelefone(professor.getTelefone());
		pr.setDataNascimento(professor.getDataNascimento());
		pr.setEstadocivil(professor.getEstadocivil());
		pr.setGrau(professor.getGrau());
		pr.setCartaoMembro(professor.getCartaoMembro());
		
		
	}
	
	
	
	@Transactional(readOnly = true)
	public Professor buscarPorIdEUsuario(Long id, String email) {
		return repository.findById(id)
				.orElseThrow(() -> new AcessoNegadoException("Acesso negado ao usuário: " + email));
	}


	@Transactional(readOnly = false)
	public void remover(Long id) {
		repository.deleteById(id);
	}


	public List<Professor> buscarProfessorPorPessoa(Long pessoa) {

		return repository.findProfessorByPessoa(pessoa);
	}

	public Map<String, Object> bucarHistoricoProfessorPorCongregacao(Long congregacao, HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.CONVERTIDOS);
		Page<HistoricoProfessor> page = repository.findHistoricoProfessorCongregacao(congregacao,
				datatables.getPageable());
		return datatables.getResponse(page);
	}

	public Map<String, Object> bucarHistoricoProfessor(HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.CONVERTIDOS);
		Page<HistoricoProfessor> page = repository.findByProfessor(datatables.getPageable());
		return datatables.getResponse(page);
	}

	public List<Professor> buscarTodosProfessoresPorCongregacao(Long congregacao) {
		// TODO Auto-generated method stub
		return repository.findAllByCongregacao(congregacao);
	}

//	public Map<String, Object> bucarHistoricoMatriculadoPorCongregacao(Long congregacao, HttpServletRequest request) {
//		datatables.setRequest(request);
//		datatables.setColunas(DatatablesColunas.CONVERTIDOS);
//		Page<HistoricoProfessor> page = repository.findHistoricoMatriculadoCongregacao(congregacao,
//				datatables.getPageable());
//		return datatables.getResponse(page);
//	}

//	public Map<String, Object> bucarHistoricoProfessorPorPessoaCongregacao(Long congregacao, Long pessoa, HttpServletRequest request) {
//		datatables.setRequest(request);
//		datatables.setColunas(DatatablesColunas.CONVERTIDOS);
//		Page<HistoricoProfessor> page = repository.findHistoricoProfessorPessoaCongregacao(congregacao,pessoa, datatables.getPageable());
//
//		return datatables.getResponse(page);
//	}

//	public Map<String, Object> bucarHistoricoConcluidoPorCongregacao(Long congregacao, HttpServletRequest request) {
//		datatables.setRequest(request);
//		datatables.setColunas(DatatablesColunas.CONVERTIDOS);
//		Page<HistoricoProfessor> page = repository.findHistoricoConcluidoCongregacao(congregacao,
//				datatables.getPageable());
//		return datatables.getResponse(page);
//	}



}
