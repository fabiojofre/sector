package com.jofre.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jofre.domain.Aula;
import com.jofre.domain.Congregacao;
import com.jofre.domain.Convertido;
import com.jofre.domain.Licao;
import com.jofre.domain.Pessoa;
import com.jofre.domain.Professor;
import com.jofre.service.AulaService;
import com.jofre.service.ConvertidoService;
import com.jofre.service.LicaoService;
import com.jofre.service.PessoaService;
import com.jofre.service.ProfessorService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("aulas")
public class AulaController {

	
	@Autowired
	private AulaService service;
		
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private ProfessorService professorService;
	
	@Autowired
	private ConvertidoService convertidoService;
	
	@Autowired
	private LicaoService licaoService;
	
	@PreAuthorize("hasAnyAuthority('DISCIPULADO','CAMPANHA')")
	@GetMapping({"", "/"})
	public String abrir(Aula aula) {
		return "aula/aula";
	}
	
	
	// salvar aula
	@PreAuthorize("hasAnyAuthority('DISCIPULADO','CAMPANHA')")
	@PostMapping("/salvar")
	public String salvar(Aula aula, RedirectAttributes attr, @AuthenticationPrincipal User user) {

		Pessoa pessoa = pessoaService.buscarPorUsuarioEmail(user.getUsername());
		Congregacao congregacao  = pessoa.getCongregacao();
		aula.setCongregacao(congregacao);
		aula.setPessoa(pessoa);
		
		service.salvar(aula);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso!");

			return "redirect:/aulas/";
	}
	
	
	@PreAuthorize("hasAnyAuthority('ESPECIALISTA','CAMPANHA')")
	@GetMapping("/historico/aula")
	public String historicoAula() {

		return "aula/historico-aula";
	}
	
	@PreAuthorize("hasAnyAuthority('ESPECIALISTA','CAMPANHA')")
	@GetMapping("/datatables/server/aula")
	public ResponseEntity<?> listaAulasCongregagacao(HttpServletRequest request, @AuthenticationPrincipal User user) {
		Pessoa pessoa = pessoaService.buscarPorUsuarioEmail(user.getUsername());
		return ResponseEntity.ok(service.buscarAulasPorCongregacao(request,pessoa.getCongregacao().getId()));
	}
		
	
	// localizar aula pelo id e envia-lo para a pagina de cadastro PARA LIBERAR
	
			@PreAuthorize("hasAnyAuthority('DISCIPULADO','CAMPANHA')")
			@GetMapping("/editar/{id}")
			public String preEditarcAula(@PathVariable("id") Long id, ModelMap model,
					@AuthenticationPrincipal User user) {

				Aula aula = service.buscarPorIdEUsuario(id, user.getUsername());
				
					System.out.println(" O ID do aula é: -> "+ aula.getId());
				model.addAttribute("aula", aula);
				return "aula/aula";
			}

			@PreAuthorize("hasAnyAuthority('DISCIPULADO','CAMPANHA')")
			@PostMapping("/editar")
			public String editarAula(Aula aula, RedirectAttributes attr, @AuthenticationPrincipal User user) {
				System.out.println("Já nesse caso, o id desse método é: "+aula.getId());
				
				service.editar(aula,user.getUsername());
				
				attr.addFlashAttribute("sucesso", "Aula foi alterada com sucesso.");
				return "redirect:/aulas/historico/aula";
			}
	
			
			@PreAuthorize("hasAnyAuthority('DISCIPULADO','CAMPANHA')")
			@ModelAttribute("professores")
			public List<Professor>listarProfessores(@AuthenticationPrincipal User user){
				Pessoa pessoa = pessoaService.buscarPorUsuarioEmail(user.getUsername());
				return professorService.buscarTodosProfessoresPorCongregacao(pessoa.getCongregacao().getId());
			}
			
			@PreAuthorize("hasAnyAuthority('DISCIPULADO','CAMPANHA')")
			@ModelAttribute("convertidos")
			public List<Convertido>listarConvertidosPorCongregacao(@AuthenticationPrincipal User user){
				Pessoa pessoa = pessoaService.buscarPorUsuarioEmail(user.getUsername());
				return convertidoService.buscarTodosConvertidosPorCongregacao(pessoa.getCongregacao().getId());
			}
			
			
			@PreAuthorize("hasAnyAuthority('DISCIPULADO','CAMPANHA')")
			@ModelAttribute("licoes")
			public List<Licao>listarLicoes(@AuthenticationPrincipal User user){
				return licaoService.buscarTodos();
			}
	
			@PreAuthorize("hasAnyAuthority('DISCIPULADO','CAMPANHA')")
			@GetMapping("/excluir/{id}")
			public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
				service.remover(id);
				attr.addFlashAttribute("sucesso", "Registro excluído com sucesso.");
				return "redirect:/aulas/";
			}
			
			

}
