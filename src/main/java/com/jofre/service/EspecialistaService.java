package com.jofre.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jofre.domain.Especialista;
import com.jofre.repository.EspecialistaRepository;

@Service
public class EspecialistaService {

	@Autowired
	private EspecialistaRepository repository;
	
	@Transactional(readOnly = true)
	public Especialista buscarPorUsuarioId(Long id) {
		
		return repository.findByUsuarioId(id).orElse(new Especialista());
	}

	@Transactional(readOnly = false)
	public void salvar(Especialista especialista) {
		
		repository.save(especialista);
	}

	@Transactional(readOnly = false)
	public void editar(Especialista especialista) {
		Especialista m2 = repository.findById(especialista.getId()).get();
		m2.setCrm(especialista.getCrm());
		m2.setDtInscricao(especialista.getDtInscricao());
		m2.setNome(especialista.getNome());
		if (!especialista.getEspecialidades().isEmpty()) {
			m2.getEspecialidades().addAll(especialista.getEspecialidades());
		}
	}

	@Transactional(readOnly = true)
	public Especialista buscarPorEmail(String email) {
		
		return repository.findByUsuarioEmail(email).orElse(new Especialista());
	}

	@Transactional(readOnly = false)
	public void excluirEspecialidadePorespecialista(Long idMed, Long idEsp) {
		Especialista especialista = repository.findById(idMed).get();
		especialista.getEspecialidades().removeIf(e -> e.getId().equals(idEsp));
	}

	@Transactional(readOnly = true)
	public List<Especialista> buscarespecialistasPorEspecialidade(String titulo) {
		
		return repository.findByespecialistasPorEspecialidade(titulo);
	}

	@Transactional(readOnly = true)
	public boolean existeEspecialidadeAgendada(Long idMed, Long idEsp) {
		
		return repository.hasEspecialidadeAgendada(idMed, idEsp).isPresent();
	}
}
