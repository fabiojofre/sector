package com.jofre.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jofre.domain.Limitacao;
import com.jofre.service.LimitacaoService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("limitacoes")
public class LimitacaoController {
	
	@Autowired
	private LimitacaoService service;
	
	@GetMapping({"", "/"})
	public String abrir(Limitacao limitacao) {

		return "limitacao/limitacao";
	}

	@PostMapping("/salvar")
	public String salvar(Limitacao limitacao, RedirectAttributes attr) {
		service.salvar(limitacao);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso!");
		return "redirect:/limitacoes";
	}
	
	@GetMapping("/datatables/server")
	public ResponseEntity<?> getLimitacaos(HttpServletRequest request) {

		return ResponseEntity.ok(service.buscarLimitacoes(request));
	}
	
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("limitacao", service.buscarPorId(id));
		return "limitacao/limitacao";
	}
	
	@GetMapping("/excluir/{id}")
	public String abrir(@PathVariable("id") Long id, RedirectAttributes attr) {
		service.remover(id);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso.");
		return "redirect:/limitacoes";
	}
	
	@GetMapping("/titulo")
	public ResponseEntity<?> getLimitacaosPorTermo(@RequestParam("termo") String termo) {
		List<String> limitacoes = service.buscarLimitacaoByTermo(termo);
		return ResponseEntity.ok(limitacoes);
	}
		
		
}
