
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

import com.jofre.domain.Congregacao;
import com.jofre.domain.Convertido;
import com.jofre.domain.OrigemConversao;
import com.jofre.domain.PerfilTipo;
import com.jofre.domain.Pessoa;
import com.jofre.service.CongregacaoService;
import com.jofre.service.ConvertidoService;
import com.jofre.service.OrigemConversaoService;
import com.jofre.service.PessoaService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("convertidos")
public class ConvertidoController {

	@Autowired
	private ConvertidoService service;
	@Autowired
	private PessoaService pessoaService;
	@Autowired
	private CongregacaoService congregacaoService;
	
	@Autowired
	private OrigemConversaoService origemConversaoService;
	
	@PreAuthorize("hasAnyAuthority('PESSOA', 'DISCIPULADO', 'CAMPANHA','ASSISTENTE','ESPECIALISTA')")
	@GetMapping({ "", "/" })
	public String abrir(Convertido convertido) {

		return "convertido/convertido";

	}

	@PreAuthorize("hasAnyAuthority('PESSOA', 'DISCIPULADO','ESPECIALISTA', 'CAMPANHA','ASSISTENTE')")
	@PostMapping({ "/salvar" })
	public String salvar(Convertido convertido, RedirectAttributes attr, @AuthenticationPrincipal User user) {
		Pessoa pessoa = pessoaService.buscarPorUsuarioEmail(user.getUsername());
		Long id_congreg = convertido.getCongregacao().getId();
		Long id_origem = convertido.getOrigemConversao().getId();
		Congregacao congregacao = congregacaoService.buscarPorId(id_congreg);
		OrigemConversao origemConversao = origemConversaoService.buscarPorId(id_origem);
		
//		System.out.println("O nome da congregacao é :"+convertido.getCongregacao().toString());
		convertido.setCongregacao(congregacao);
		convertido.setPessoa(pessoa);
		convertido.setOrigemConversao(origemConversao);
		convertido.setConvertido(true);
		service.salvar(convertido);
		attr.addFlashAttribute("sucesso", "Convertido cadastrado com sucesso.");
		return "redirect:/convertidos/";

	}

	// abrir histórico de convertidos
	@PreAuthorize("hasAnyAuthority('PESSOA', 'DISCIPULADO','ESPECIALISTA', 'CAMPANHA','ASSISTENTE')")
	@GetMapping("/historico/convertido")
	public String historico() {

		return "convertido/historico-convertido";
	}


	
	// localizar histórico de convertido por pessoa
	@PreAuthorize("hasAnyAuthority('PESSOA', 'DISCIPULADO','ESPECIALISTA', 'CAMPANHA','ASSISTENTE')")
	@GetMapping("/datatables/server/historico")
	public ResponseEntity<?> historicoConvertidoPorEmail(HttpServletRequest request,
			@AuthenticationPrincipal User user) {
		if (user.getAuthorities().contains(new SimpleGrantedAuthority(PerfilTipo.PESSOA.getDesc()))) {

			return ResponseEntity.ok(service.bucarHistoricoConvertidoPorPessoa(user.getUsername(), request));
			
		}if (user.getAuthorities().contains(new SimpleGrantedAuthority(PerfilTipo.CAMPANHA.getDesc()))) {
			Pessoa pessoa =  pessoaService.buscarPorUsuarioEmail(user.getUsername());
			return ResponseEntity.ok(service.bucarHistoricoConvertidoPorCongregacao(pessoa.getCongregacao().getId(), request));
		}
		return ResponseEntity.badRequest().build();
	}
	//******************Área***************************
	// abrir histórico de convertidos por área
	@PreAuthorize("hasAnyAuthority('PESSOA', 'DISCIPULADO','ESPECIALISTA', 'CAMPANHA','ASSISTENTE')")
	@GetMapping("/historico/convertido-area")
	public String historicoArea() {

		return "convertido/historico-convertido-area";
	}	
	
	// localizar histórico de convertido por area
	@PreAuthorize("hasAnyAuthority('PESSOA', 'DISCIPULADO','ESPECIALISTA', 'CAMPANHA','ASSISTENTE')")
	@GetMapping("/datatables/server/historico-area")
	public ResponseEntity<?> historicoConvertidoPorEmailArea(HttpServletRequest request,
			@AuthenticationPrincipal User user) {
		LocalDate data = LocalDate.now();
		if (user.getAuthorities().contains(new SimpleGrantedAuthority(PerfilTipo.ASSISTENTE.getDesc()))) {
			Pessoa pessoa =  pessoaService.buscarPorUsuarioEmail(user.getUsername());
			return ResponseEntity.ok(service.bucarHistoricoConvertidoPorAreaEData(pessoa.getCongregacao().getArea(), request));
		}
		return ResponseEntity.badRequest().build();
	}
	
	//****************Setor************************
	
	// abrir histórico de convertidos por área
	@PreAuthorize("hasAnyAuthority('PESSOA', 'DISCIPULADO','ESPECIALISTA', 'CAMPANHA','ASSISTENTE')")
		@GetMapping("/historico/convertido-setor")
		public String historicoSetor() {

			return "convertido/historico-convertido-setor";
		}	
		
		// localizar histórico de convertido por area
	@PreAuthorize("hasAnyAuthority('PESSOA', 'DISCIPULADO','ESPECIALISTA', 'CAMPANHA','ASSISTENTE')")
		@GetMapping("/datatables/server/historico-setor")
		public ResponseEntity<?> historicoConvertidoPorEmailSetor(HttpServletRequest request,
				@AuthenticationPrincipal User user) {
		
			if (user.getAuthorities().contains(new SimpleGrantedAuthority(PerfilTipo.ESPECIALISTA.getDesc()))) {
				
				return ResponseEntity.ok(service.bucarHistoricoConvertidoPorSetorEData(request));
			}
			return ResponseEntity.badRequest().build();
		}
	
	
	// abrir histórico de convertidos por área
		@PreAuthorize("hasAnyAuthority('PESSOA', 'DISCIPULADO','ESPECIALISTA', 'CAMPANHA','ASSISTENTE')")
			@GetMapping("/historico/convertido-setor-area")
			public String historicoSetorArea() {

				return "convertido/historico-convertido-setor-area";
			}	
			
			// localizar histórico de convertido por area
		@PreAuthorize("hasAnyAuthority('PESSOA', 'DISCIPULADO','ESPECIALISTA', 'CAMPANHA','ASSISTENTE')")
			@GetMapping("/datatables/server/historico-setor-area")
			public ResponseEntity<?> historicoConvertidoPorEmailSetorArea(HttpServletRequest request,
					@AuthenticationPrincipal User user) {
			
				if (user.getAuthorities().contains(new SimpleGrantedAuthority(PerfilTipo.ESPECIALISTA.getDesc()))) {
					
					return ResponseEntity.ok(service.bucarHistoricoConvertidoPorSetorAreaSemana(request));
				}
				return ResponseEntity.badRequest().build();
			}
		
//	*************************************************
	
	// localizar convertido pelo id e envia-lo para a pagina de cadastro
	@PreAuthorize("hasAnyAuthority('PESSOA', 'DISCIPULADO','ESPECIALISTA', 'CAMPANHA','ASSISTENTE')")
		@GetMapping("/editar/convertido/{id}")
		public String preEditarcConvertidoPessoa(@PathVariable("id") Long id, ModelMap model,
				@AuthenticationPrincipal User user) {

			Convertido convertido = service.buscarPorIdEUsuario(id,user.getUsername());
			convertido.setArea(null);
				System.out.println(" O ID do convertido é: -> "+ convertido.getId());
			model.addAttribute("convertido", convertido);
			return "convertido/convertido";
		}

	@PreAuthorize("hasAnyAuthority('PESSOA', 'DISCIPULADO','ESPECIALISTA', 'CAMPANHA','ASSISTENTE')")
		@PostMapping("/editar")
		public String editarConvertido(Convertido convertido, RedirectAttributes attr, @AuthenticationPrincipal User user) {
			System.out.println("Já nesse caso, o id desse método é: "+convertido.getId());
			Pessoa pessoa = pessoaService.buscarPorUsuarioEmail(user.getUsername());
			Long id_congreg = convertido.getCongregacao().getId();
			Long id_origem = convertido.getOrigemConversao().getId();
			Congregacao congregacao = congregacaoService.buscarPorId(id_congreg);
			OrigemConversao origemConversao = origemConversaoService.buscarPorId(id_origem);
			convertido.setPessoa(pessoa);
			convertido.setCongregacao(congregacao);
			convertido.setOrigemConversao(origemConversao);
			service.editar(convertido,user.getUsername());
			
			attr.addFlashAttribute("sucesso", "Seu convertido foi alterado com sucesso.");
			return "redirect:/convertidos/historico/convertido";
		}

	@PreAuthorize("hasAnyAuthority('PESSOA', 'DISCIPULADO','ESPECIALISTA', 'CAMPANHA','ASSISTENTE')")
		@GetMapping("/excluir/convertido/{id}")
		public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
			service.remover(id);
			attr.addFlashAttribute("sucesso", "Convertido excluído com sucesso.");
			return "redirect:/convertidos/historico/convertido";
		}


	@PreAuthorize("hasAnyAuthority('PESSOA', 'DISCIPULADO','ESPECIALISTA', 'CAMPANHA','ASSISTENTE')")
		@ModelAttribute("origens_conversao")
		public List<OrigemConversao>listarOrigems(){
			return origemConversaoService.buscarTodosOrdenados();
		}
}
