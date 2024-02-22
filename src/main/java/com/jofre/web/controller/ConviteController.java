package com.jofre.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jofre.domain.Congregacao;
import com.jofre.domain.Convite;
import com.jofre.service.CongregacaoService;
import com.jofre.service.ConviteService;

@Controller
@RequestMapping("convites")
public class ConviteController {

	
	@Autowired
	private ConviteService service;
	
	@Autowired
	private CongregacaoService congregacaoService;
	
	@PreAuthorize("hasAuthority('ESPECIALISTA')")
	@GetMapping({"", "/"})
	public String abrir(Convite convite) {
		return "convite/convite";
	}
	
	// salvar convite
	@PreAuthorize("hasAuthority('ESPECIALISTA')")
	@PostMapping("/salvar")
	public String salvar(Convite convite, RedirectAttributes attr) {
	try {
		service.salvar(convite);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso!");
	} catch (Exception e) {
		attr.addFlashAttribute("falha", "Cadastro não realizado. /n"+e);
	}
		
		return "convite/convite";
	}
	
	@PreAuthorize("hasAuthority('ESPECIALISTA')")
	@ModelAttribute("congregacoes")
	public List<Congregacao>listaCongregaces(){
		return congregacaoService.buscarTodos();
	}
	
}
