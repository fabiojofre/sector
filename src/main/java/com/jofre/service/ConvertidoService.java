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
import com.jofre.domain.Convertido;
import com.jofre.exception.AcessoNegadoException;
import com.jofre.repository.ConvertidoRepository;
import com.jofre.repository.projection.HistoricoConvertido;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ConvertidoService {

	@Autowired
	private ConvertidoRepository repository;

	@Autowired
	private Datatables datatables;

	@Transactional(readOnly = false)
	public void salvar(Convertido convertido) {

		repository.save(convertido);
	}

	@Transactional(readOnly = true)
	public Map<String, Object> buscarConvertidos(HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.CONVERTIDOS);
		Page<?> page = datatables.getSearch().isEmpty() ? repository.findAll(datatables.getPageable())
				: repository.findAllByNome(datatables.getSearch(), datatables.getPageable());
		return datatables.getResponse(page);
	}

	@Transactional(readOnly = true)
	public Convertido buscarPorId(Long id) {
		return repository.findById(id).get();
	}

	@Transactional(readOnly = false)
	public void editar(Convertido convertido, String email) {
		Convertido co = buscarPorIdEUsuario(convertido.getId(), email);
		co.setArea(convertido.getArea());
		co.setNome(convertido.getNome());
		co.setConvertido(convertido.getConvertido());
		co.setInativo(convertido.getInativo());
		co.setObsConversao(convertido.getObsConversao());
		co.setTelefone(convertido.getTelefone());
		co.setEndereco(convertido.getEndereco());
		co.setCongregacao(convertido.getCongregacao());
		co.setDataNascimento(convertido.getDataNascimento());
		co.setDataConversao(convertido.getDataConversao());
		if(!(convertido.getMatriculado() == null || convertido.getMatriculado() == false)) {
			co.setMatriculado(true);
			co.setDataMatriculado(LocalDate.now());
			co.setEstadocivil(convertido.getEstadocivil());
			co.setCiclo(convertido.getCiclo());
			co.setBatismo(convertido.getBatismo());
			co.setDaUniao(convertido.getDaUniao());
			co.setArea(convertido.getCongregacao().getArea());
		}
	}
	
	
	

//	@Transactional(readOnly = true)
//	public Convertido buscarPorIdEUsuario(Long id, String email) {
//		return repository.findByIdAndPessoaEmail(id, email)
//				.orElseThrow(() -> new AcessoNegadoException("Acesso negado ao usuário: " + email));
//	}
	
	@Transactional(readOnly = true)
	public Convertido buscarPorIdEUsuario(Long id, String email) {
		return repository.findById(id)
				.orElseThrow(() -> new AcessoNegadoException("Acesso negado ao usuário: " + email));
	}


	@Transactional(readOnly = false)
	public void remover(Long id) {
		repository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Map<String, Object> bucarHistoricoConvertidoPorPessoa(String email, HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.CONVERTIDOS);
		Page<HistoricoConvertido> page = repository.findHistoricoConvertidoEmail(email, datatables.getPageable());

		return datatables.getResponse(page);
	}

	public List<Convertido> buscarConvertidoPorPessoa(Long pessoa) {

		return repository.findConvertidoByPessoa(pessoa);
	}

	public Map<String, Object> bucarHistoricoConvertidoPorCongregacao(Long congregacao, HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.CONVERTIDOS);
		Page<HistoricoConvertido> page = repository.findHistoricoConvertidoCongregacao(congregacao,
				datatables.getPageable());
		return datatables.getResponse(page);
	}

	public Map<String, Object> bucarHistoricoMatriculadoPorCongregacao(Long congregacao, HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.CONVERTIDOS);
		Page<HistoricoConvertido> page = repository.findHistoricoMatriculadoCongregacao(congregacao,
				datatables.getPageable());
		return datatables.getResponse(page);
	}

	public Map<String, Object> bucarHistoricoConvertidoPorPessoaCongregacao(Long congregacao, Long pessoa, HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.CONVERTIDOS);
		Page<HistoricoConvertido> page = repository.findHistoricoConvertidoPessoaCongregacao(congregacao,pessoa, datatables.getPageable());

		return datatables.getResponse(page);
	}

	public Map<String, Object> bucarHistoricoConcluidoPorCongregacao(Long congregacao, HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.CONVERTIDOS);
		Page<HistoricoConvertido> page = repository.findHistoricoConcluidoCongregacao(congregacao,
				datatables.getPageable());
		return datatables.getResponse(page);
	}



}
