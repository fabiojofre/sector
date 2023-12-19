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

import com.jofre.domain.Cargo;
import com.jofre.service.CargoService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("cargos")
public class CargoController {
	
	@Autowired
	private CargoService service;
	
	@GetMapping({"", "/"})
	public String abrir(Cargo Cargo) {

		return "cargo/cargo";
	}

	@PostMapping("/salvar")
	public String salvar(Cargo Cargo, RedirectAttributes attr) {
		service.salvar(Cargo);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso!");
		return "redirect:/cargos";
	}
	
	@GetMapping("/datatables/server")
	public ResponseEntity<?> getCargos(HttpServletRequest request) {

		return ResponseEntity.ok(service.buscarCargos(request));
	}
	
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("Cargo", service.buscarPorId(id));
		return "cargo/cargo";
	}
	
	@GetMapping("/excluir/{id}")
	public String abrir(@PathVariable("id") Long id, RedirectAttributes attr) {
		service.remover(id);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso.");
		return "redirect:/cargos";
	}
	
	@GetMapping("/titulo")
	public ResponseEntity<?> getCargosPorTermo(@RequestParam("termo") String termo) {
		List<String> cargos = service.buscarCargoByTermo(termo);
		return ResponseEntity.ok(cargos);
	}
		
		
}
