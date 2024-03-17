package com.jofre.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jofre.domain.Tipo;
import com.jofre.repository.TipoRepository;

@Service
public class TipoService {
	
	@Autowired
	private TipoRepository repository;
	
	@Transactional(readOnly = false)
	public void salvar(Tipo tipo) {
		repository.save(tipo);
	}
	
	@Transactional(readOnly = true)
	public Tipo buscarPorId(Long id) {
		return repository.findById(id).get();
	}
	
	@Transactional(readOnly = false)
	public void remover(Long id) {
		repository.deleteById(id);
	}
	
	@Transactional(readOnly = true)
	public List<Tipo> buscarTodos() {
		return repository.findAll();
	} 

}
