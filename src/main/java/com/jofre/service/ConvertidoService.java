package com.jofre.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jofre.datatables.Datatables;
import com.jofre.datatables.DatatablesColunas;
import com.jofre.domain.Convertido;
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
		Page<?> page = datatables.getSearch().isEmpty()
				? repository.findAll(datatables.getPageable())
				: repository.findAllByNome(datatables.getSearch(), datatables.getPageable());
		return datatables.getResponse(page);
	}
	@Transactional(readOnly = true)
	public Convertido buscarPorId(Long id) {
		return repository.findById(id).get();
	}
	
	@Transactional(readOnly = false)
	public void editar(Convertido convertido) {
		Convertido co = repository.findById(convertido.getId()).get();
		co.setNome(convertido.getNome());
		co.setConvertido(convertido.getConvertido());
		co.setInativo(convertido.getInativo());
		co.setObsConversao(convertido.getObsConversao());
		co.setTelefone(convertido.getTelefone());
		co.setEndereco(convertido.getEndereco());
		co.setCongregacao(convertido.getCongregacao());
		co.setDataNascimento(convertido.getDataNascimento());
	}

	
	@Transactional(readOnly = false)
	public void remover(Long id) {
		repository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Map<String,Object> bucarHistoricoConvertidoPorPessoa(String email, HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.CONVERTIDOS);
		Page<HistoricoConvertido>page = repository.findHistoricoConvertidoEmail(email,datatables.getPageable());
	
		return datatables.getResponse(page);
	}

	public List<Convertido> buscarConvertidoPorPessoa(Long pessoa) {
		
		return repository.findConvertidoByPessoa(pessoa);
	}
	
	

}
















