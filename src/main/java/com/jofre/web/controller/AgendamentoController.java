package com.jofre.web.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jofre.domain.Agendamento;
import com.jofre.domain.Especialidade;
import com.jofre.domain.PerfilTipo;
import com.jofre.domain.Pessoa;
import com.jofre.service.AgendamentoService;
import com.jofre.service.EspecialidadeService;
import com.jofre.service.PessoaService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("agendamentos")
public class AgendamentoController {
	
	@Autowired
	private AgendamentoService service;
	@Autowired
	private PessoaService pessoaService;
	@Autowired
	private EspecialidadeService especialidadeService;	

	// abre a pagina de agendamento de consultas 
	@PreAuthorize("hasAnyAuthority('PESSOA', 'ESPECIALISTA')")
	@GetMapping({"/agendar"})
	public String agendarConsulta(Agendamento agendamento) {

		return "agendamento/cadastro";		
	}
	
	// busca os horarios livres, ou seja, sem agendamento
	@PreAuthorize("hasAnyAuthority('PESSOA', 'ESPECIALISTA')")
	@GetMapping("/horario/especialista/{id}/data/{data}")
	public ResponseEntity<?> getHorarios(@PathVariable("id") Long id,
						@PathVariable("data") @DateTimeFormat(iso = ISO.DATE) LocalDate data) {

		return ResponseEntity.ok(service.buscarHorariosNaoAgendadosPorespecialistaIdEData(id, data));
	}
	
	// salvar um consulta agendada
	@PreAuthorize("hasAuthority('PESSOA')")
	@PostMapping({"/salvar"})
	public String salvar(@AuthenticationPrincipal User user, RedirectAttributes attr, Agendamento agendamento) {
		Pessoa pessoa = pessoaService.buscarPorUsuarioEmail(user.getUsername());
		String titulo = agendamento.getEspecialidade().getTitulo();
		Especialidade especialidade = especialidadeService
				.buscarPorTitulos(new String[] {titulo})
				.stream().findFirst().get();
		agendamento.setEspecialidade(especialidade);
		agendamento.setPessoa(pessoa);
		service.salvar(agendamento);
		attr.addFlashAttribute("sucesso", "Sua consulta foi agendada com sucesso.");
		return "redirect:/agendamentos/agendar";		
	}
	
	// abrir pagina de historico de agendamento do pessoa
	@PreAuthorize("hasAnyAuthority('PESSOA', 'ESPECIALISTA')")
	@GetMapping({"/historico/pessoa", "/historico/consultas"})
	public String historico() {

		return "agendamento/historico-pessoa";
	}
	
	// localizar o historico de agendamentos por usuario logado
	@PreAuthorize("hasAnyAuthority('PESSOA', 'ESPECIALISTA')")
	@GetMapping("/datatables/server/historico")
	public ResponseEntity<?> historicoAgendamentosPorPessoa(HttpServletRequest request, 
															  @AuthenticationPrincipal User user) {
		
		if (user.getAuthorities().contains(new SimpleGrantedAuthority(PerfilTipo.PESSOA.getDesc()))) {
			
			return ResponseEntity.ok(service.buscarHistoricoPorPessoaEmail(user.getUsername(), request));
		}
		
		if (user.getAuthorities().contains(new SimpleGrantedAuthority(PerfilTipo.ESPECIALISTA.getDesc()))) {
			
			return ResponseEntity.ok(service.buscarHistoricoPorespecialistaEmail(user.getUsername(), request));
		}		
		
		return ResponseEntity.notFound().build();
	}
	
	// localizar agendamento pelo id e envia-lo para a pagina de cadastro
	@PreAuthorize("hasAnyAuthority('PESSOA', 'ESPECIALISTA')")
	@GetMapping("/editar/consulta/{id}") 
	public String preEditarConsultaPessoa(@PathVariable("id") Long id, 
										    ModelMap model, @AuthenticationPrincipal User user) {
		
		Agendamento agendamento = service.buscarPorIdEUsuario(id, user.getUsername());
		
		model.addAttribute("agendamento", agendamento);
		return "agendamento/cadastro";
	}
	
	@PreAuthorize("hasAnyAuthority('PESSOA', 'ESPECIALISTA')")
	@PostMapping("/editar")
	public String editarConsulta(Agendamento agendamento, 
						         RedirectAttributes attr, @AuthenticationPrincipal User user) {
		String titulo = agendamento.getEspecialidade().getTitulo();
		Especialidade especialidade = especialidadeService
				.buscarPorTitulos(new String[] {titulo})
				.stream().findFirst().get();
		agendamento.setEspecialidade(especialidade);
		
		service.editar(agendamento, user.getUsername());
		attr.addFlashAttribute("sucesso", "Sua consulta froi alterada com sucesso.");
		return "redirect:/agendamentos/agendar";
	}
	
	@PreAuthorize("hasAuthority('PESSOA')")
	@GetMapping("/excluir/consulta/{id}")
	public String excluirConsulta(@PathVariable("id") Long id, RedirectAttributes attr) {
		service.remover(id);
		attr.addFlashAttribute("sucesso", "Consulta excluída com sucesso.");
		return "redirect:/agendamentos/historico/pessoa";
	}

}
