package com.jofre.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.jofre.domain.Perfil;
import com.jofre.domain.PerfilTipo;
import com.jofre.domain.Pessoa;
import com.jofre.domain.Usuario;
import com.jofre.service.PessoaService;
import com.jofre.service.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

	@Autowired
	UsuarioService service;

	@Autowired
	PessoaService pessoaService;

	// abrir pagina home
	@GetMapping({ "/", "/home" })
	public String home(HttpServletResponse response, @AuthenticationPrincipal User user) {

		if (user != null) {
			try {
				Usuario us = new Usuario();
				us = service.buscarPorEmail(user.getUsername());

				Pessoa pessoa = new Pessoa();
				pessoa = pessoaService.buscarPorUsuarioEmailValido(user.getUsername());

				if ((!us.getPerfis().contains(new Perfil(PerfilTipo.ADMIN.getCod())))
						&& !us.getPerfis().contains(new Perfil(PerfilTipo.ESPECIALISTA.getCod())) && (pessoa == null)) {
					return "redirect:/pessoas/dados";
				}
			} catch (Exception e) {
			}
		}
		return "home";
	}

//
//	// abrir pagina home
//	@GetMapping({"/", "/home"})
//	public String home(HttpServletResponse response) {
//		
//		return "home";
//	}	

	// abrir pagina login
	@GetMapping({ "/login" })
	public String login() {

		return "login";
	}

	// login invalido
	@GetMapping({ "/login-error" })
	public String loginError(ModelMap model, HttpServletRequest resp) {
		HttpSession session = resp.getSession();
		String lastException = String.valueOf(session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION"));
		if (lastException.contains(SessionAuthenticationException.class.getName())) {
			model.addAttribute("alerta", "erro");
			model.addAttribute("titulo", "Acesso recusado!");
			model.addAttribute("texto", "Você já está logado em outro dispositivo.");
			model.addAttribute("subtexto", "Faça o logout ou espere sua sessão expirar.");
			return "login";
		}
		model.addAttribute("alerta", "erro");
		model.addAttribute("titulo", "Crendenciais inválidas!");
		model.addAttribute("texto", "Login ou senha incorretos, tente novamente.");
		model.addAttribute("subtexto", "Acesso permitido apenas para cadastros já ativados.");
		return "login";
	}

	@GetMapping("/expired")
	public String sessaoExpirada(ModelMap model) {
		model.addAttribute("alerta", "erro");
		model.addAttribute("titulo", "Acesso recusado!");
		model.addAttribute("texto", "Sua sessão expirou.");
		model.addAttribute("subtexto", "Você logou em outro dispositivo");
		model.clear();
		model.clone();
		return "login";
	}

	// acesso negado
	@GetMapping({ "/acesso-negado" })
	public String acessoNegado(ModelMap model, HttpServletResponse resp) {
		model.addAttribute("status", resp.getStatus());
		model.addAttribute("error", "Acesso Negado");
		model.addAttribute("message", "Você não tem permissão para acesso a esta área ou ação.");
		return "error";
	}
}
