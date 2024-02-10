
package com.jofre.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jofre.domain.Congregacao;
import com.jofre.domain.Convertido;
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

	@PreAuthorize("hasAnyAuthority('PESSOA', 'ESPECIALISTA')")
	@GetMapping({ "", "/" })
	public String abrir(Convertido convertido) {

		return "convertido/convertido";

	}
	@PostMapping({  "/salvar" })
	public String salvar(Convertido convertido, RedirectAttributes attr, @AuthenticationPrincipal User user) {
		Pessoa pessoa = pessoaService.buscarPorUsuarioEmail(user.getUsername());
		Long id_congreg = convertido.getCongregacao().getId();
		Congregacao congregacao = congregacaoService.buscarPorId(id_congreg);
		
		System.out.println("O nome da congregacao é :"+convertido.getCongregacao().toString());
		convertido.setCongregacao(congregacao);
		convertido.setPessoa(pessoa);
		convertido.setConvertido(true);
		service.salvar(convertido);
		attr.addFlashAttribute("sucesso", "Convertido cadastrado com sucesso.");
		return "redirect:/convertidos/";

	}
	// abrir sistórico de convertidos
	@PreAuthorize("hasAnyAuthority('PESSOA', 'ESPECIALISTA')")
	@GetMapping("/historico/convertido")
	public String historico() {
		
		return "convertido/historico-convertido";
	}
	
	//localizar histórico de convertido por pessoa
	@GetMapping("/datatables/server/historico")
	public ResponseEntity<?> historicoConvertidoPorEmail(HttpServletRequest request,
												@AuthenticationPrincipal User user ){
		if (user.getAuthorities().contains(new SimpleGrantedAuthority(PerfilTipo.PESSOA.getDesc()))) {
			
			return ResponseEntity.ok(service.bucarHistoricoConvertidoPorPessoa(user.getUsername(),request));
		}
		return ResponseEntity.badRequest().build();
	}
//	
//	@GetMapping("/datatebles/server")
//	public ResponseEntity<?>buscarConvertidoPorPessoa(@PathVariable("pssoa") Long pessoa,@AuthenticationPrincipal User user ){
//		 pessoa = pessoaService.buscarPorUsuarioEmail(user.getUsername()).getId();
//		return ResponseEntity.ok(service.buscarConvertidoPorPessoa(pessoa));
//	}
//	
	
}




