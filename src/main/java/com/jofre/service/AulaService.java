package com.jofre.service;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jofre.datatables.Datatables;
import com.jofre.datatables.DatatablesColunas;
import com.jofre.domain.Aula;
import com.jofre.exception.AcessoNegadoException;
import com.jofre.repository.AulaRepository;
import com.jofre.repository.projection.HistoricoAula;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AulaService {

	@Autowired
	AulaRepository repository;
	
	@Autowired
	private Datatables datatables;

	@Transactional(readOnly = false)
	public void salvar(Aula aula) {
		aula.setDataLancamento(LocalDate.now());
		repository.save(aula);
		
	}

	@Transactional(readOnly = true)
	public Aula buscarPorIdEUsuario(Long id, String email) {
		return repository.findById(id)
				.orElseThrow(() -> new AcessoNegadoException("Acesso negado ao usuário: " + email));
	}

	@Transactional(readOnly = false)
	public void editar(Aula aula, String email) {
		Aula al = buscarPorIdEUsuario(aula.getId(), email);
		
		al.setLicao(aula.getLicao());
		al.setDataLancamento(aula.getDataLancamento());
		al.setProfessor(aula.getProfessor());
		al.setConvertidos(aula.getConvertidos());
	}

	@Transactional(readOnly = true)
	public  Map<String, Object> buscarAulasPorCongregacao(HttpServletRequest request,Long congregacao) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.AULAS);
		Page<HistoricoAula> page =  repository.findAllAulaByCongregacao(datatables.getPageable(),congregacao);		
		return datatables.getResponse(page);
		
	}
	
//	@Transactional(readOnly = true)
//	public  Map<String, Object> buscarAulasPorAluno(HttpServletRequest request,Long aluno) {
//		datatables.setRequest(request);
//		datatables.setColunas(DatatablesColunas.AULAS);
//		Page<HistoricoAula> page =  repository.findAllAulasByAlunos(datatables.getPageable(),aluno);		
//		return datatables.getResponse(page);
//		
//	}
//	
	
	@Transactional(readOnly = false)
	public void remover(Long id) {
		repository.deleteById(id);
		
	}
//
//	@Transactional(readOnly = true)
//	public  Map<String, Object> buscarAulasRecebidos(HttpServletRequest request, Long congregacao) {
//		datatables.setRequest(request);
//		datatables.setColunas(DatatablesColunas.CONVITES);
//		Page<HistoricoAula> page =  repository.findAllAulaRecebidosByCongregacao(datatables.getPageable(),congregacao);		
//		System.out.println("Impressão = "+page.get().toString());
//		return datatables.getResponse(page);
//	}
//	
	
}
