package com.jofre.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jofre.domain.Especialista;
import com.jofre.domain.Usuario;
import com.jofre.service.EspecialistaService;
import com.jofre.service.UsuarioService;

@Controller 
@RequestMapping("especialistas")
public class EspecialistaController {
	
	@Autowired
	private EspecialistaService service;
	@Autowired
	private UsuarioService usuarioService;

	// abrir pagina de dados pessoais de especialistas pelo especialista
	@GetMapping({"/dados"})
	public String abrirPorespecialista(Especialista especialista, ModelMap model, @AuthenticationPrincipal User user) {
		if (especialista.hasNotId()) {
			especialista = service.buscarPorEmail(user.getUsername());
			model.addAttribute("especialista", especialista);
		}
		return "especialista/cadastro";
	}
	
	// salvar especialista
	@PostMapping({"/salvar"})
	public String salvar(Especialista especialista, RedirectAttributes attr, @AuthenticationPrincipal User user) {
		if (especialista.hasNotId() && especialista.getUsuario().hasNotId()) {
			Usuario usuario = usuarioService.buscarPorEmail(user.getUsername());
			especialista.setUsuario(usuario);
		}
		service.salvar(especialista);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso.");
		attr.addFlashAttribute("especialista", especialista);
		return "redirect:/especialistas/dados";
	}

	// salvar especialista via formulario ajax
/*	@PostMapping({"/salvar/ajax"})
	public String salvarAjax(@RequestBody especialistaDto especialistaDto, RedirectAttributes attr, @AuthenticationPrincipal User user) {
		especialistaDto.getNome();
		//service.salvar(especialista);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso.");
		//attr.addFlashAttribute("especialista", especialista);
		return "redirect:/especialistas/dados";
	}*/

	// editar especialista
	@PostMapping({"/editar"})
	public String editar(Especialista especialista, RedirectAttributes attr) {
		service.editar(especialista);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso.");
		attr.addFlashAttribute("especialista", especialista);
		return "redirect:/especialistas/dados";		
	}
	
	// excluir especialidade
	@GetMapping({"/id/{idMed}/excluir/especializacao/{idEsp}"})
	public String excluirEspecialidadePorespecialista(@PathVariable("idMed") Long idMed, 
						 @PathVariable("idEsp") Long idEsp, RedirectAttributes attr) {
		
		if ( service.existeEspecialidadeAgendada(idMed, idEsp) ) {
			attr.addFlashAttribute("falha", "Tem consultas agendadas, exclusão negada.");
		} else {		
			service.excluirEspecialidadePorespecialista(idMed, idEsp);
			attr.addFlashAttribute("sucesso", "Especialidade removida com sucesso.");
		}
		return "redirect:/especialistas/dados";		
	}
	
	// buscar especialistas por especialidade via ajax
	@GetMapping("/especialidade/titulo/{titulo}")
	public ResponseEntity<?> getespecialistasPorEspecialidade(@PathVariable("titulo") String titulo) {

		return ResponseEntity.ok(service.buscarespecialistasPorEspecialidade(titulo));
	}	
	
}
