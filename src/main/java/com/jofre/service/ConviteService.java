package com.jofre.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jofre.datatables.Datatables;
import com.jofre.domain.Convite;
import com.jofre.repository.ConviteRepository;

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
}
