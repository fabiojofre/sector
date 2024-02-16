package com.jofre.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jofre.domain.Ciclo;
import com.jofre.repository.CicloRepository;

@Service
public class CicloService {

	@Autowired
	private CicloRepository repository;
	
	@Transactional(readOnly = false)
	public void salvar(Ciclo ciclo) {
		repository.save(ciclo);		
	}
	
	
	@Transactional(readOnly = true)
	public Ciclo buscarPorId(Long id) {
		return repository.findById(id).get();
	}

	@Transactional(readOnly = false)
	public void remover(Long id) {
		repository.deleteById(id);
	}
	
	
	@Transactional(readOnly = true)
	public List<Ciclo> buscarTodos() {
		return repository.findAll();
	} 
	
}
