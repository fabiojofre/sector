package com.jofre.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jofre.datatables.Datatables;
import com.jofre.datatables.DatatablesColunas;
import com.jofre.domain.Convite;
import com.jofre.exception.AcessoNegadoException;
import com.jofre.repository.ConviteRepository;
import com.jofre.repository.projection.HistoricoConvite;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ConviteService {

	@Autowired
	ConviteRepository repository;
	
	@Autowired
	private Datatables datatables;

	@Transactional(readOnly = false)
	public void salvar(Convite convite) {
		repository.save(convite);
		
	}

	@Transactional(readOnly = true)
	public Convite buscarPorIdEUsuario(Long id, String email) {
		return repository.findById(id)
				.orElseThrow(() -> new AcessoNegadoException("Acesso negado ao usuário: " + email));
	}

	@Transactional(readOnly = false)
	public void editar(Convite convite, String email) {
		Convite co = buscarPorIdEUsuario(convite.getId(), email);
		
		co.setArea(convite.getArea());
		co.setCongregacao(convite.getCongregacao());
		co.setAtivo(convite.getAtivo());
		co.setLiberado(convite.getLiberado());
		co.setDataEvento(convite.getDataEvento());
		co.setCongregacoes(convite.getCongregacoes());
		
	}

	@Transactional(readOnly = true)
	public  Map<String, Object> buscarConvites(HttpServletRequest request,Long congregacao) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.CONVITES);
		Page<HistoricoConvite> page =  repository.findAllConviteByCongregacao(datatables.getPageable(),congregacao);		
		return datatables.getResponse(page);
		
	}
	
	@Transactional(readOnly = true)
	public  Map<String, Object> buscarConvitesLiberados(HttpServletRequest request,Long congregacao) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.CONVITES);
		Page<HistoricoConvite> page =  repository.findAllConviteLiberadosByCongregacao(datatables.getPageable(),congregacao);		
		System.out.println("Impressão = "+page.get().toString());
		return datatables.getResponse(page);
		
	}
	
	@Transactional(readOnly = false)
	public void remover(Long id) {
		repository.deleteById(id);
		
	}
	
	
}
