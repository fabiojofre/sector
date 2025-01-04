package com.jofre.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jofre.domain.Licao;
import com.jofre.repository.LicaoRepository;

@Service
public class LicaoService {

	@Autowired
	private LicaoRepository repository;
	
	@Transactional(readOnly = false)
	public void salvar(Licao licao) {
		repository.save(licao);				
	}
	
	
	@Transactional(readOnly = true)
	public List<Licao> buscarTodos() {
		return repository.findAll();
	} 
		
}
