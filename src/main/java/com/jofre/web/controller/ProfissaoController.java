package com.jofre.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jofre.domain.Profissao;
import com.jofre.service.ProfissaoService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("profissoes")
public class ProfissaoController {
	
	@Autowired
	private ProfissaoService service;
	
	
	@GetMapping({"", "/"})
	public String abrir(Profissao profissao) {

		return "profissao/profissao";
	}
	
	
	@PostMapping("/salvar")
	public String salvar(Profissao profissao, RedirectAttributes attr) {
		service.salvar(profissao);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso!");
		return "redirect:/profissoes";
	}
	
	
	@GetMapping("/datatables/server")
	public ResponseEntity<?> getProfissaos(HttpServletRequest request) {

		return ResponseEntity.ok(service.buscarProfissoes(request));
	}
	
	
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("profissao", service.buscarPorId(id));
		return "profissao/profissao";
	}
	
	@GetMapping("/excluir/{id}")
	public String abrir(@PathVariable("id") Long id, RedirectAttributes attr) {
		service.remover(id);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso.");
		return "redirect:/profissoes";
	}
	
	
	@GetMapping("/titulo")
	public ResponseEntity<?> getProfissaosPorTermo(@RequestParam("termo") String termo) {
		List<String> profissoes = service.buscarProfissaoByTermo(termo);
		return ResponseEntity.ok(profissoes);
	}
		
		
}
