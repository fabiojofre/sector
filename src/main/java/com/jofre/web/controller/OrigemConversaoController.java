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

import com.jofre.domain.OrigemConversao;
import com.jofre.service.OrigemConversaoService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("origens_conversao")
public class OrigemConversaoController {
	
	@Autowired
	private OrigemConversaoService service;
	
	@GetMapping({"", "/"})
	public String abrir(OrigemConversao origemCOnversao) {

		return "origem_conversao/origem_conversao";
	}

	@PostMapping("/salvar")
	public String salvar(OrigemConversao origemConversao, RedirectAttributes attr) {
		service.salvar(origemConversao);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso!");
		return "redirect:/origens_conversao";
	}
	
	@GetMapping("/datatables/server")
	public ResponseEntity<?> getOrigems(HttpServletRequest request) {

		return ResponseEntity.ok(service.buscarOrigens(request));
	}
	
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("origem_conversao", service.buscarPorId(id));
		return "origem_conversao/origem_conversao";
	}
	
	@GetMapping("/excluir/{id}")
	public String abrir(@PathVariable("id") Long id, RedirectAttributes attr) {
		service.remover(id);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso.");
		return "redirect:/origens_conversao";
	}
	
	@GetMapping("/titulo")
	public ResponseEntity<?> getOrigemsPorTermo(@RequestParam("termo") String termo) {
		List<String> origens = service.buscarOrigemByTermo(termo);
		return ResponseEntity.ok(origens);
	}
		
		
}
