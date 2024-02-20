
package com.jofre.web.controller;

import java.time.LocalDate;
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

import com.jofre.domain.Ciclo;
import com.jofre.domain.Congregacao;
import com.jofre.domain.Convertido;
import com.jofre.domain.EstadoCivil;
import com.jofre.domain.PerfilTipo;
import com.jofre.domain.Pessoa;
import com.jofre.service.CicloService;
import com.jofre.service.CongregacaoService;
import com.jofre.service.ConvertidoService;
import com.jofre.service.EstadoCivilService;
import com.jofre.service.PessoaService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("discipulados")
public class DiscipuladoController {

	@Autowired
	private ConvertidoService service;
	@Autowired
	private PessoaService pessoaService;
	@Autowired
	private CongregacaoService congregacaoService;
	@Autowired
	private CicloService cicloService;
	@Autowired
	private EstadoCivilService civilService;
	
	@PreAuthorize("hasAnyAuthority('PESSOA', 'DISCIPULADO','CAMPANHA')")
	@GetMapping({ "", "/" })
	public String abrir(Convertido convertido) {

		return "discipulado/convertido";

	}

	@PreAuthorize("hasAnyAuthority('PESSOA', 'DISCIPULADO','CAMPANHA')")
	@PostMapping({ "/salvar" })
	public String salvar(Convertido convertido, RedirectAttributes attr, @AuthenticationPrincipal User user) {
		Pessoa pessoa = pessoaService.buscarPorUsuarioEmail(user.getUsername());
		Congregacao congregacao = pessoa.getCongregacao();
	
//		System.out.println("O nome da congregacao é :"+convertido.getCongregacao().toString());
		convertido.setDataMatriculado(LocalDate.now());
		convertido.setMatriculado(true);
		convertido.setConvertido(false);
		convertido.setInativo(false);
		convertido.setConcluinte(false);
		convertido.setCongregacao(congregacao);
		convertido.setPessoa(pessoa);
		convertido.setArea(congregacao.getArea());
		
		service.salvar(convertido);
		attr.addFlashAttribute("sucesso", "Aluno cadastrado com sucesso.");
		return "redirect:/discipulados/";

	}
	
	
	//01 convertidos
	// abrir histórico de convertidos
	@PreAuthorize("hasAnyAuthority('PESSOA', 'ESPECIALISTA','DISCIPULADO')")
	@GetMapping("/historico/convertido")
	public String historico() {

		return "discipulado/historico-convertido";
	}

	// localizar histórico de convertido
		@PreAuthorize("hasAnyAuthority('PESSOA','DISCIPULADO')")
		@GetMapping("/datatables/server/historicoconvertido")
		public ResponseEntity<?> historicoConvertidoPorEmail(HttpServletRequest request,
				@AuthenticationPrincipal User user) {
			if (!user.getAuthorities().contains(new SimpleGrantedAuthority(PerfilTipo.ESPECIALISTA.getDesc()))) {

				Pessoa pessoa = pessoaService.buscarPorUsuarioEmail(user.getUsername());
				return ResponseEntity.ok(service.bucarHistoricoConvertidoPorCongregacao(pessoa.getCongregacao().getId(), request));
			}
			return ResponseEntity.badRequest().build();
		}
	
	
	
	// abrir histórico de MATRICULADOS
		@PreAuthorize("hasAnyAuthority('PESSOA', 'ESPECIALISTA','DISCIPULADO','CAMPANHA')")
		@GetMapping("/historico/discipulado")
		public String historicoDiscipulado() {

			return "discipulado/historico-discipulado";
		}
	
	

	// localizar histórico de matriculado
	@PreAuthorize("hasAnyAuthority('PESSOA','DISCIPULADO','CAMPANHA')")
	@GetMapping("/datatables/server/historicodiscipulado")
	public ResponseEntity<?> historicoDiscipuladoPorEmail(HttpServletRequest request,
			@AuthenticationPrincipal User user) {
		if (!user.getAuthorities().contains(new SimpleGrantedAuthority(PerfilTipo.ESPECIALISTA.getDesc()))) {

			Pessoa pessoa = pessoaService.buscarPorUsuarioEmail(user.getUsername());
			return ResponseEntity.ok(service.bucarHistoricoMatriculadoPorCongregacao(pessoa.getCongregacao().getId(), request));
		}
		return ResponseEntity.badRequest().build();
	}
	
	
	// abrir histórico de concluintes
			@PreAuthorize("hasAnyAuthority('PESSOA', 'ESPECIALISTA','DISCIPULADO','CAMPANHA')")
			@GetMapping("/historico/concluido")
			public String historicoConcluintes() {

				return "discipulado/historico-concluido";
			}
		
		

		// localizar histórico de matriculado
		@PreAuthorize("hasAnyAuthority('PESSOA','DISCIPULADO','CAMPANHA')")
		@GetMapping("/datatables/server/historicoconcluido")
		public ResponseEntity<?> historicoConcluidoPorEmail(HttpServletRequest request,
				@AuthenticationPrincipal User user) {
			if (!user.getAuthorities().contains(new SimpleGrantedAuthority(PerfilTipo.ESPECIALISTA.getDesc()))) {

				Pessoa pessoa = pessoaService.buscarPorUsuarioEmail(user.getUsername());
				return ResponseEntity.ok(service.bucarHistoricoConcluidoPorCongregacao(pessoa.getCongregacao().getId(), request));
			}
			return ResponseEntity.badRequest().build();
		}
		
	
	
	
	// localizar convertido pelo id e envia-lo para a pagina de cadastro
		@PreAuthorize("hasAnyAuthority('PESSOA', 'DISCIPULADO','CAMPANHA')")
		@GetMapping("/editar/convertido/{id}")
		public String preEditarConvertidoPessoa(@PathVariable("id") Long id, ModelMap model,
				@AuthenticationPrincipal User user) {
				
			Convertido convertido = service.buscarPorIdEUsuario(id,user.getUsername());
			convertido.setMatriculado(true);
				System.out.println(" O ID do convertido é: -> "+ convertido.getId());
			model.addAttribute("convertido", convertido);
			return "discipulado/convertido";
		}

		@PreAuthorize("hasAnyAuthority('PESSOA', 'DISCIPULADO','CAMPANHA')")
		@PostMapping("/editar")
		public String editarConvertido(Convertido convertido, RedirectAttributes attr, @AuthenticationPrincipal User user) {
			System.out.println("Já nesse caso, o id desse método é: "+convertido.getId());
			Pessoa pessoa = pessoaService.buscarPorUsuarioEmail(user.getUsername());
			
			Congregacao congregacao = pessoa.getCongregacao();
			convertido.setPessoa(pessoa);
			convertido.setCongregacao(congregacao);
			convertido.setMatriculado(true);
									
			service.editar(convertido,user.getUsername());
			
			attr.addFlashAttribute("sucesso", "Seu convertido foi alterado com sucesso.");
			return "redirect:/discipulados/historico/discipulado";
		}

		@PreAuthorize("hasAnyAuthority('PESSOA', 'DISCIPULADO', 'CAMPANHA')")
		@GetMapping("/excluir/convertido/{id}")
		public String excluirConvertido(@PathVariable("id") Long id, RedirectAttributes attr) {
			service.remover(id);
			attr.addFlashAttribute("sucesso", "Convertido excluído com sucesso.");
			return "redirect:/discipulados/historico/convertido";
		}
		
		@PreAuthorize("hasAnyAuthority('PESSOA', 'DISCIPULADO','CAMPANHA')")
		@GetMapping("/concluir/convertido/{id}")
		public String concluirConvertido(@PathVariable("id") Long id, RedirectAttributes attr) {
			Convertido convertido = service.buscarPorId(id);
			convertido.setConcluinte(true);
			convertido.setDataConclusao(LocalDate.now());
			service.salvar(convertido);
			attr.addFlashAttribute("sucesso", "Convertido formado com sucesso.");
			return "redirect:/discipulados/historico/discipulado";
		}
		
		@PreAuthorize("hasAnyAuthority('PESSOA', 'DISCIPULADO','CAMPANHA')")
		@GetMapping("/rematricular/convertido/{id}")
		public String rematricularDiscipulado(@PathVariable("id") Long id, RedirectAttributes attr) {
			Convertido convertido = service.buscarPorId(id);
			convertido.setConcluinte(null);
			convertido.setDataConclusao(null);
			service.salvar(convertido);
			attr.addFlashAttribute("sucesso", "Convertido formado com sucesso.");
			return "redirect:/discipulados/historico/concluido";
		}

		@PreAuthorize("hasAnyAuthority('PESSOA', 'DISCIPULADO','CAMPANHA')")
		@ModelAttribute("ciclos")
		public List<Ciclo>listaDeCiclos(){
			return cicloService.buscarTodos();
		}

		@PreAuthorize("hasAnyAuthority('PESSOA', 'DISCIPULADO','CAMPANHA')")
		@ModelAttribute("estadoCivil")
		public List<EstadoCivil>listaDeEstadoCivil(){
			return civilService.buscarTodos();
		}
}
