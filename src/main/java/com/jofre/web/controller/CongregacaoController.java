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

import com.jofre.domain.Congregacao;
import com.jofre.service.CongregacaoService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("congregacoes")
public class CongregacaoController {
	
	@Autowired
	private CongregacaoService service;
	
	@GetMapping({"", "/"})
	public String abrir(Congregacao congregacao) {

		return "congregacao/congregacao";
	}

	@PostMapping("/salvar")
	public String salvar(Congregacao congregacao, RedirectAttributes attr) {
		service.salvar(congregacao);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso!");
		return "redirect:/congregacoes";
	}
	
	@GetMapping("/datatables/server")
	public ResponseEntity<?> getCongregacoes(HttpServletRequest request) {

		return ResponseEntity.ok(service.buscarCongregacoes(request));
	}
	
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("congregacao", service.buscarPorId(id));
		return "congregacao/congregacao";
	}
	
	@GetMapping("/excluir/{id}")
	public String abrir(@PathVariable("id") Long id, RedirectAttributes attr) {
		service.remover(id);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso.");
		return "redirect:/congregacoes";
	}
	
	@GetMapping("/titulo")
	public ResponseEntity<?> getCongregacoesPorTermo(@RequestParam("termo") String termo) {
		List<String> congregacoes = service.buscarCongregacaoByTermo(termo);
		return ResponseEntity.ok(congregacoes);
	}
		
		
}
