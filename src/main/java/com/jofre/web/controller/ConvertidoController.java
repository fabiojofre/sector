
package com.jofre.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.jofre.domain.Congregacao;
import com.jofre.domain.Convertido;
import com.jofre.domain.Especialidade;
import com.jofre.domain.PerfilTipo;
import com.jofre.domain.Pessoa;
import com.jofre.service.CongregacaoService;
import com.jofre.service.ConvertidoService;
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
	
	@PreAuthorize("hasAnyAuthority('PESSOA', 'DISCIPULADO', 'CAMPANHA')")
	@GetMapping({ "", "/" })
	public String abrir(Convertido convertido) {

		return "convertido/convertido";

	}

	@PreAuthorize("hasAnyAuthority('PESSOA', 'DISCIPULADO', 'CAMPANHA')")
	@PostMapping({ "/salvar" })
	public String salvar(Convertido convertido, RedirectAttributes attr, @AuthenticationPrincipal User user) {
		Pessoa pessoa = pessoaService.buscarPorUsuarioEmail(user.getUsername());
		Long id_congreg = convertido.getCongregacao().getId();
		Congregacao congregacao = congregacaoService.buscarPorId(id_congreg);

//		System.out.println("O nome da congregacao é :"+convertido.getCongregacao().toString());
		convertido.setCongregacao(congregacao);
		convertido.setPessoa(pessoa);
		convertido.setConvertido(true);
		service.salvar(convertido);
		attr.addFlashAttribute("sucesso", "Convertido cadastrado com sucesso.");
		return "redirect:/convertidos/";

	}

	// abrir histórico de convertidos
	@PreAuthorize("hasAnyAuthority('PESSOA', 'ESPECIALISTA', 'CAMPANHA')")
	@GetMapping("/historico/convertido")
	public String historico() {

		return "convertido/historico-convertido";
	}

	// localizar histórico de convertido por pessoa
	@PreAuthorize("hasAnyAuthority('PESSOA', 'DISCIPULADO', 'CAMPANHA')")
	@GetMapping("/datatables/server/historico")
	public ResponseEntity<?> historicoConvertidoPorEmail(HttpServletRequest request,
			@AuthenticationPrincipal User user) {
		if (user.getAuthorities().contains(new SimpleGrantedAuthority(PerfilTipo.PESSOA.getDesc()))) {

			return ResponseEntity.ok(service.bucarHistoricoConvertidoPorPessoa(user.getUsername(), request));
			
		}if (user.getAuthorities().contains(new SimpleGrantedAuthority(PerfilTipo.CAMPANHA.getDesc()))) {
			Pessoa pessoa =  pessoaService.buscarPorUsuarioEmail(user.getUsername());
			return ResponseEntity.ok(service.bucarHistoricoConvertidoPorPessoaCongregacao(pessoa.getCongregacao().getId(),pessoa.getId(), request));
		}
		return ResponseEntity.badRequest().build();
	}
	
	// localizar convertido pelo id e envia-lo para a pagina de cadastro
		@PreAuthorize("hasAnyAuthority('PESSOA', 'DISCIPULADO','CAMPANHA')")
		@GetMapping("/editar/convertido/{id}")
		public String preEditarcConvertidoPessoa(@PathVariable("id") Long id, ModelMap model,
				@AuthenticationPrincipal User user) {

			Convertido convertido = service.buscarPorIdEUsuario(id,user.getUsername());
			convertido.setArea(null);
				System.out.println(" O ID do convertido é: -> "+ convertido.getId());
			model.addAttribute("convertido", convertido);
			return "convertido/convertido";
		}

		@PreAuthorize("hasAnyAuthority('PESSOA', 'DISCIPULADO', 'CAMPANHA')")
		@PostMapping("/editar")
		public String editarConvertido(Convertido convertido, RedirectAttributes attr, @AuthenticationPrincipal User user) {
			System.out.println("Já nesse caso, o id desse método é: "+convertido.getId());
			Pessoa pessoa = pessoaService.buscarPorUsuarioEmail(user.getUsername());
			Long id_congreg = convertido.getCongregacao().getId();
			Congregacao congregacao = congregacaoService.buscarPorId(id_congreg);
			convertido.setPessoa(pessoa);
			convertido.setCongregacao(congregacao);
			service.editar(convertido,user.getUsername());
			
			attr.addFlashAttribute("sucesso", "Seu convertido foi alterado com sucesso.");
			return "redirect:/convertidos/historico/convertido";
		}

		@PreAuthorize("hasAnyAuthority('PESSOA', 'CAMPANHA')")
		@GetMapping("/excluir/convertido/{id}")
		public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
			service.remover(id);
			attr.addFlashAttribute("sucesso", "Convertido excluído com sucesso.");
			return "redirect:/convertidos/historico/convertido";
		}


}
