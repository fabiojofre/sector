package com.jofre.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jofre.domain.Congregacao;
import com.jofre.repository.CongregacaoRepository;

@Service
public class CongregacaoService {

	@Autowired
	private CongregacaoRepository repository;
	
	

	@Transactional(readOnly = false)
	public void salvar(Congregacao congregacao) {
		repository.save(congregacao);		
	}

	
}
