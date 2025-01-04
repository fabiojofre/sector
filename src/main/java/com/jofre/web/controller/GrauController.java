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

import com.jofre.domain.Grau;
import com.jofre.service.GrauService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("graus")
public class GrauController {
	
	@Autowired
	private GrauService service;
	
	@GetMapping({"", "/"})
	public String abrir(Grau grau) {

		return "grau/grau";
	}

	@PostMapping("/salvar")
	public String salvar(Grau grau, RedirectAttributes attr) {
		service.salvar(grau);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso!");
		return "redirect:/graus";
	}
	
	@GetMapping("/datatables/server")
	public ResponseEntity<?> getGraus(HttpServletRequest request) {

		return ResponseEntity.ok(service.buscarGraus(request));
	}
	
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("grau", service.buscarPorId(id));
		return "grau/grau";
	}
	
	@GetMapping("/excluir/{id}")
	public String abrir(@PathVariable("id") Long id, RedirectAttributes attr) {
		service.remover(id);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso.");
		return "redirect:/graus";
	}
	
	@GetMapping("/titulo")
	public ResponseEntity<?> getGrausPorTermo(@RequestParam("termo") String termo) {
		List<String> graus = service.buscarGrauByTermo(termo);
		return ResponseEntity.ok(graus);
	}
		
		
}
