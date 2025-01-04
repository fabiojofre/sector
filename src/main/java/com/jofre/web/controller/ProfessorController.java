
package com.jofre.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jofre.domain.Congregacao;
import com.jofre.domain.EstadoCivil;
import com.jofre.domain.Grau;
import com.jofre.domain.PerfilTipo;
import com.jofre.domain.Pessoa;
import com.jofre.domain.Professor;
import com.jofre.service.CongregacaoService;
import com.jofre.service.EstadoCivilService;
import com.jofre.service.GrauService;
import com.jofre.service.PessoaService;
import com.jofre.service.ProfessorService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("professores")
public class ProfessorController {

	@Autowired
	private ProfessorService service;
	@Autowired
	private PessoaService pessoaService;
	@Autowired
	private CongregacaoService congregacaoService;
	
	@Autowired
	private EstadoCivilService estadoCivilService;
	
	@Autowired
	private GrauService grauService;
	
	@PreAuthorize("hasAnyAuthority('CAMPANHA','ASSISTENTE')")
	@GetMapping({ "", "/" })
	public String abrir(Professor professor) {

		return "professor/professor";

	}

	@PreAuthorize("hasAnyAuthority('CAMPANHA','ASSISTENTE')")
	@PostMapping({ "/salvar" })
	public String salvar(Professor professor, RedirectAttributes attr, @AuthenticationPrincipal User user) {
		Pessoa pessoa = pessoaService.buscarPorUsuarioEmail(user.getUsername());
		Long id_congreg = pessoa.getCongregacao().getId();
		Congregacao congregacao = congregacaoService.buscarPorId(id_congreg);

		professor.setPessoa(pessoa);
		professor.setCongregacao(congregacao);
		service.salvar(professor);
		attr.addFlashAttribute("sucesso", "Professor cadastrado com sucesso.");
		return "redirect:/professores/";

	}

	// abrir histórico de professores
	@PreAuthorize("hasAnyAuthority('ESPECIALISTA', 'CAMPANHA','ASSISTENTE')")
	@GetMapping("/historico/professor")
	public String historico() {

		return "professor/historico-professor";
	}

	// localizar histórico de professor por pessoa
	@PreAuthorize("hasAnyAuthority('ESPECIALISTA', 'CAMPANHA','ASSISTENTE')")
	@GetMapping("/datatables/server/historico")
	public ResponseEntity<?> historicoProfessorPorEmail(HttpServletRequest request,
			@AuthenticationPrincipal User user) {
		if (user.getAuthorities().contains(new SimpleGrantedAuthority(PerfilTipo.ESPECIALISTA.getDesc()))) {

			return ResponseEntity.ok(service.bucarHistoricoProfessor(request));
			
		}
		if (user.getAuthorities().contains(new SimpleGrantedAuthority(PerfilTipo.CAMPANHA.getDesc()))) {
			Pessoa pessoa =  pessoaService.buscarPorUsuarioEmail(user.getUsername());
			return ResponseEntity.ok(service.bucarHistoricoProfessorPorCongregacao(pessoa.getCongregacao().getId(), request));
		}
		return ResponseEntity.badRequest().build();
	}
	
	// localizar professor pelo id e envia-lo para a pagina de cadastro
		@PreAuthorize("hasAnyAuthority('CAMPANHA','ASSISTENTE')")
		@GetMapping("/editar/professor/{id}")
		public String preEditarcProfessorPessoa(@PathVariable("id") Long id, ModelMap model,
				@AuthenticationPrincipal User user) {

			Professor professor = service.buscarPorIdEUsuario(id,user.getUsername());
				System.out.println(" O ID do professor é: -> "+ professor.getId());
			model.addAttribute("professor", professor);
			return "professor/professor";
		}

		@PreAuthorize("hasAuthority('CAMPANHA')")
		@PostMapping("/editar")
		public String editarProfessor(Professor professor, RedirectAttributes attr, @AuthenticationPrincipal User user) {
			
			service.editar(professor,user.getUsername());
			
			attr.addFlashAttribute("sucesso", "Seu professor foi alterado com sucesso.");
			return "redirect:/professores";
		}

		@PreAuthorize("hasAnyAuthority('CAMPANHA','ASSISTENTE')")
		@GetMapping("/excluir/professor/{id}")
		public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
			service.remover(id);
			attr.addFlashAttribute("sucesso", "Professor excluído com sucesso.");
			return "redirect:/professores";
		}

		@PreAuthorize("hasAnyAuthority('CAMPANHA','ASSISTENTE')")
		@ModelAttribute("estadoscivis")
		public List<EstadoCivil>listarEstadoCivil(){
			return estadoCivilService.buscarTodos();
		}
		
		@PreAuthorize("hasAnyAuthority('CAMPANHA','ASSISTENTE')")
		@ModelAttribute("graus")
		public List<Grau>listarGraus(){
			return grauService.buscarTodos();
		}

}
