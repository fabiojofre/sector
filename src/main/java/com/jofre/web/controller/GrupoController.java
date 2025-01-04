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

import com.jofre.domain.Grupo;
import com.jofre.service.GrupoService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("grupos")
public class GrupoController {
	
	@Autowired
	private GrupoService service;
	
	@GetMapping({"", "/"})
	public String abrir(Grupo grupo) {

		return "grupo/grupo";
	}

	@PostMapping("/salvar")
	public String salvar(Grupo grupo, RedirectAttributes attr) {
		service.salvar(grupo);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso!");
		return "redirect:/grupos";
	}
	
	@GetMapping("/datatables/server")
	public ResponseEntity<?> getGrupos(HttpServletRequest request) {

		return ResponseEntity.ok(service.buscarGrupos(request));
	}
	
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("grupo", service.buscarPorId(id));
		return "grupo/grupo";
	}
	
	@GetMapping("/excluir/{id}")
	public String abrir(@PathVariable("id") Long id, RedirectAttributes attr) {
		service.remover(id);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso.");
		return "redirect:/grupos";
	}
	
	@GetMapping("/titulo")
	public ResponseEntity<?> getGruposPorTermo(@RequestParam("termo") String termo) {
		List<String> grupos = service.buscarGrupoByTermo(termo);
		return ResponseEntity.ok(grupos);
	}
		
		
}
