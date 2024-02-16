package com.jofre.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jofre.domain.EstadoCivil;
import com.jofre.repository.EstadoCivilRepository;

@Service
public class EstadoCivilService {

	@Autowired
	private EstadoCivilRepository repository;
	
	@Transactional(readOnly = false)
	public void salvar(EstadoCivil estadoCivil) {
		repository.save(estadoCivil);		
	}
	
	
	@Transactional(readOnly = true)
	public EstadoCivil buscarPorId(Long id) {
		return repository.findById(id).get();
	}

	@Transactional(readOnly = false)
	public void remover(Long id) {
		repository.deleteById(id);
	}
	
	
	@Transactional(readOnly = true)
	public List<EstadoCivil> buscarTodos() {
		return repository.findAll();
	} 
	
}
