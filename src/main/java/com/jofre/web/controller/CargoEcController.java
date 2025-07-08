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

import com.jofre.domain.CargoEc;
import com.jofre.service.CargoEcService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("cargos_ec")
public class CargoEcController {
	
	@Autowired
	private CargoEcService service;
	
	@GetMapping({"", "/"})
	public String abrir(CargoEc cargoEc) {

		return "cargo_ec/cargo_ec";
	}

	@PostMapping("/salvar")
	public String salvar(CargoEc cargoEc, RedirectAttributes attr) {
		service.salvar(cargoEc);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso!");
		return "redirect:/cargos";
	}
	
	@GetMapping("/datatables/server")
	public ResponseEntity<?> getCargoEcs(HttpServletRequest request) {

		return ResponseEntity.ok(service.buscarCargos(request));
	}
	
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("cargo", service.buscarPorId(id));
		return "cargo_ec/cargo_ec";
	}
	
	@GetMapping("/excluir/{id}")
	public String abrir(@PathVariable("id") Long id, RedirectAttributes attr) {
		service.remover(id);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso.");
		return "redirect:/cargo_ec";
	}
	
	@GetMapping("/titulo")
	public ResponseEntity<?> getCargoEcsPorTermo(@RequestParam("termo") String termo) {
		List<String> cargos = service.buscarCargoByTermo(termo);
		return ResponseEntity.ok(cargos);
	}
		
		
}
