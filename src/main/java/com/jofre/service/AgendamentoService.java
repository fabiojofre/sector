package com.jofre.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jofre.datatables.Datatables;
import com.jofre.datatables.DatatablesColunas;
import com.jofre.domain.Agendamento;
import com.jofre.domain.Horario;
import com.jofre.exception.AcessoNegadoException;
import com.jofre.repository.AgendamentoRepository;
import com.jofre.repository.projection.HistoricoPessoa;

@Service
public class AgendamentoService {
	
	@Autowired
	private AgendamentoRepository repository;
	@Autowired
	private Datatables datatables;

	@Transactional(readOnly = true)
	public List<Horario> buscarHorariosNaoAgendadosPorespecialistaIdEData(Long id, LocalDate data) {
		
		return repository.findByespecialistaIdAndDataNotHorarioAgendado(id, data);
	}

	@Transactional(readOnly = false)
	public void salvar(Agendamento agendamento) {
		
		repository.save(agendamento);
	}

	@Transactional(readOnly = true)
	public Map<String, Object> buscarHistoricoPorPessoaEmail(String email, HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.AGENDAMENTOS);
		Page<HistoricoPessoa> page = repository.findHistoricoByPessoaEmail(email, datatables.getPageable());
		return datatables.getResponse(page);
	}

	@Transactional(readOnly = true)
	public Map<String, Object> buscarHistoricoPorespecialistaEmail(String email, HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.AGENDAMENTOS);
		Page<HistoricoPessoa> page = repository.findHistoricoByespecialistaEmail(email, datatables.getPageable());
		return datatables.getResponse(page);
	}

	@Transactional(readOnly = true)
	public Agendamento buscarPorId(Long id) {
		
		return repository.findById(id).get();
	}

	@Transactional(readOnly = false)
	public void editar(Agendamento agendamento, String email) {
		Agendamento ag = buscarPorIdEUsuario(agendamento.getId(), email);
		ag.setDataConsulta(agendamento.getDataConsulta());
		ag.setEspecialidade(agendamento.getEspecialidade());
		ag.setHorario(agendamento.getHorario());
		ag.setEspecialista(agendamento.getEspecialista());
				
	}

	@Transactional(readOnly = true)
	public Agendamento buscarPorIdEUsuario(Long id, String email) {
		
		return repository
				.findByIdAndPessoaOrespecialistaEmail(id, email)
				.orElseThrow(() -> new AcessoNegadoException("Acesso negado ao usuário: " + email));
	}

	@Transactional(readOnly = false)
	public void remover(Long id) {

		repository.deleteById(id);
	}

}
