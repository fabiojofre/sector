package com.jofre.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jofre.domain.Congregacao;
import com.jofre.domain.Pessoa;
import com.jofre.domain.Usuario;
import com.jofre.service.CongregacaoService;
import com.jofre.service.PessoaService;
import com.jofre.service.UsuarioService;

@Controller
@RequestMapping("pessoas")
public class PessoaController {
	
	@Autowired
	private PessoaService service;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private CongregacaoService congregacaoService;

	// abrir pagina de dados pessoais do pessoa
	@GetMapping("/dados")
	public String cadastrar(Pessoa pessoa, ModelMap model, @AuthenticationPrincipal User user) {
		pessoa = service.buscarPorUsuarioEmail(user.getUsername());
		if (pessoa.hasNotId()) {
			pessoa.setUsuario(new Usuario(user.getUsername()));
		}
		model.addAttribute("pessoa", pessoa);
		return "pessoa/cadastro";
	}
	
	// salvar o form de dados pessoais do pessoa com verificacao de senha
	@PostMapping("/salvar")
	public String salvar(Pessoa pessoa, ModelMap model, @AuthenticationPrincipal User user) {
		Usuario u = usuarioService.buscarPorEmail(user.getUsername());
		Long id_congreg = pessoa.getCongregacao().getId();
		Congregacao congregacao = congregacaoService.buscarPorId(id_congreg);
		pessoa.setCongregacao(congregacao);
		System.out.println("O nome da congregacao da pessoa é :"+ pessoa.getCongregacao().toString());
		if (UsuarioService.isSenhaCorreta(pessoa.getUsuario().getSenha(), u.getSenha())) {
			pessoa.setUsuario(u);
			service.salvar(pessoa);
			model.addAttribute("sucesso", "Seus dados foram inseridos com sucesso.");
		} else {
			model.addAttribute("falha", "Sua senha não confere, tente novamente.");
		}
		return "pessoa/cadastro";
	}	
	
	// editar o form de dados pessoais do pessoa com verificacao de senha
	@PostMapping("/editar")
	public String editar(Pessoa pessoa, ModelMap model, @AuthenticationPrincipal User user) {
		Usuario u = usuarioService.buscarPorEmail(user.getUsername());
	
		if (UsuarioService.isSenhaCorreta(pessoa.getUsuario().getSenha(), u.getSenha())) {
			service.editar(pessoa);
			model.addAttribute("sucesso", "Seus dados foram editados com sucesso.");
		} else {
			model.addAttribute("falha", "Sua senha não confere, tente novamente.");
		}
		return "pessoa/cadastro";
	}	
		
	
}
