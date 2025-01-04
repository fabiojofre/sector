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
import com.jofre.domain.Congregado;
import com.jofre.domain.Convertido;
import com.jofre.exception.AcessoNegadoException;
import com.jofre.repository.CongregadoRepository;
import com.jofre.repository.projection.HistoricoCongregado;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class CongregadoService {

	@Autowired
	CongregadoRepository repository;
	
	@Autowired
	private Datatables datatables;

	@Transactional(readOnly = false)
	public void salvar(Congregado congregado) {
		repository.save(congregado);
		
	}

	@Transactional(readOnly = true)
	public Congregado buscarPorIdEUsuario(Long id, String email) {
		return repository.findById(id)
				.orElseThrow(() -> new AcessoNegadoException("Acesso negado ao usuário: " + email));
	}

	@Transactional(readOnly = false)
	public void editar(Congregado congregado, String email) {
		Congregado co = buscarPorIdEUsuario(congregado.getId(), email);
		co.setNome(congregado.getNome());
		co.setTelefone(congregado.getTelefone());
		co.setCartaoMembro(congregado.getCartaoMembro());
		co.setUsuarioalteracao(congregado.getUsuarioalteracao());
		co.setDataAlteracao(LocalDate.now());
		
	}

	@Transactional(readOnly = true)
	public  Map<String, Object> buscarCongregados(HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.CONGREGADOS);
		Page<HistoricoCongregado> page =  repository.findByCongregado(datatables.getPageable());		
		return datatables.getResponse(page);
		
	}
	public  Map<String, Object> buscarCongregadosPorCongregacao(Long congregacao_id,HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.CONGREGADOS);
		Page<HistoricoCongregado> page =  repository.findAllCongregadoByCongregacao(datatables.getPageable(),congregacao_id);		
		return datatables.getResponse(page);
		
	}
	
	@Transactional(readOnly =true)
	public List<Congregado>buscarPorCartao(Long cartaoMembro){
	return repository.findByCartao(cartaoMembro);
	}
	
	@Transactional(readOnly = false)
	public void remover(Long id) {
		repository.deleteById(id);
		
	}

	
	@Transactional(readOnly =true)
	public List<Congregado>buscarPorCPF(String cpf){
		// TODO Auto-generated method stub
		return repository.findByCPF(cpf);
	}

		
	

}
