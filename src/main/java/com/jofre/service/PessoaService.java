package com.jofre.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jofre.domain.Pessoa;
import com.jofre.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository repository;
	
	@Transactional(readOnly = true)
	public Pessoa buscarPorUsuarioEmail(String email) {
		
		return repository.findByUsuarioEmail(email).orElse(new Pessoa());
	}

	@Transactional(readOnly = false)
	public void salvar(Pessoa pessoa) {
		
		repository.save(pessoa);		
	}

	@Transactional(readOnly = false)
	public void editar(Pessoa pessoa) {
		Pessoa p2 = repository.findById(pessoa.getId()).get();
		p2.setNome(pessoa.getNome());
		p2.setDtNascimento(pessoa.getDtNascimento());	
		p2.setArea(pessoa.getArea());
		p2.setCongregacao(pessoa.getCongregacao());
		p2.setTelefone(pessoa.getTelefone());
		p2.setDtNascimento(pessoa.getDtNascimento());
	}
}
