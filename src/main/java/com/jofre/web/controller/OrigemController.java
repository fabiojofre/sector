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

import com.jofre.domain.Origem;
import com.jofre.service.OrigemService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("origens")
public class OrigemController {
	
	@Autowired
	private OrigemService service;
	
	@GetMapping({"", "/"})
	public String abrir(Origem origem) {

		return "origem/origem";
	}

	@PostMapping("/salvar")
	public String salvar(Origem origem, RedirectAttributes attr) {
		service.salvar(origem);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso!");
		return "redirect:/origens";
	}
	
	@GetMapping("/datatables/server")
	public ResponseEntity<?> getOrigems(HttpServletRequest request) {

		return ResponseEntity.ok(service.buscarOrigens(request));
	}
	
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("origem", service.buscarPorId(id));
		return "origem/origem";
	}
	
	@GetMapping("/excluir/{id}")
	public String abrir(@PathVariable("id") Long id, RedirectAttributes attr) {
		service.remover(id);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso.");
		return "redirect:/origens";
	}
	
	@GetMapping("/titulo")
	public ResponseEntity<?> getOrigemsPorTermo(@RequestParam("termo") String termo) {
		List<String> origens = service.buscarOrigemByTermo(termo);
		return ResponseEntity.ok(origens);
	}
		
		
}
