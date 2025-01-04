package com.jofre.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jofre.domain.Congregacao;
import com.jofre.domain.Convite;
import com.jofre.domain.Pessoa;
import com.jofre.domain.Tipo;
import com.jofre.service.CongregacaoService;
import com.jofre.service.ConviteService;
import com.jofre.service.PessoaService;
import com.jofre.service.TipoService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("convites")
public class ConviteController {

	
	@Autowired
	private ConviteService service;
	
	@Autowired
	private CongregacaoService congregacaoService;
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private TipoService tipoService;
	
	@PreAuthorize("hasAnyAuthority('ESPECIALISTA','CAMPANHA')")
	@GetMapping({"", "/"})
	public String abrir(Convite convite) {
		return "convite/convite";
	}
	
	@PreAuthorize("hasAnyAuthority('ESPECIALISTA','CAMPANHA')")
	@GetMapping({ "/convidar"})
	public String abrirConvidar(Convite convite) {
		return "convite/conviteEditar";
	}
	
	// salvar convite
	@PreAuthorize("hasAnyAuthority('ESPECIALISTA','CAMPANHA')")
	@PostMapping("/salvar")
	public String salvar(Convite convite, RedirectAttributes attr, @AuthenticationPrincipal User user) {

		convite.setAtivo(true);
		convite.setLiberado(true);
		service.salvar(convite);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso!");

			return "redirect:/convites/";
	}
	
	@PreAuthorize("hasAnyAuthority('ESPECIALISTA','CAMPANHA')")
	@GetMapping("/datatables/server/listapendentes")
	public ResponseEntity<?> getConvitesPendentes(HttpServletRequest request, @AuthenticationPrincipal User user) {
		Pessoa pessoa = pessoaService.buscarPorUsuarioEmail(user.getUsername());
		return ResponseEntity.ok(service.buscarConvites(request,pessoa.getCongregacao().getId()));
	}
	
	//listagem liberados
	
	@PreAuthorize("hasAnyAuthority('ESPECIALISTA','CAMPANHA')")
	@GetMapping("/historico/liberados")
	public String historicoLiberados() {

		return "convite/historico-liberado";
	}
	
	@PreAuthorize("hasAnyAuthority('ESPECIALISTA','CAMPANHA')")
	@GetMapping("/datatables/server/listaliberados")
	public ResponseEntity<?> getConvitesLiberados(HttpServletRequest request, @AuthenticationPrincipal User user) {
		Pessoa pessoa = pessoaService.buscarPorUsuarioEmail(user.getUsername());
		return ResponseEntity.ok(service.buscarConvitesLiberados(request,pessoa.getCongregacao().getId()));
	}
	
	
	//listagem recebidos
	
	@PreAuthorize("hasAnyAuthority('ESPECIALISTA','CAMPANHA')")
	@GetMapping("/historico/recebidos")
	public String historicoRecebidos() {

		return "convite/historico-recebido";
	}
	
	@PreAuthorize("hasAnyAuthority('ESPECIALISTA','CAMPANHA')")
	@GetMapping("/datatables/server/listarecebidos")
	public ResponseEntity<?> getConvitesRecebidos(HttpServletRequest request, @AuthenticationPrincipal User user) {
		Pessoa pessoa = pessoaService.buscarPorUsuarioEmail(user.getUsername());
		return ResponseEntity.ok(service.buscarConvitesRecebidos(request,pessoa.getCongregacao().getId()));
	}
	
	
	
	
	
	// localizar convite pelo id e envia-lo para a pagina de cadastro PARA LIBERAR
	
			@PreAuthorize("hasAnyAuthority('ESPECIALISTA','CAMPANHA')")
			@GetMapping("/editar/{id}")
			public String preEditarcConvitePessoa(@PathVariable("id") Long id, ModelMap model,
					@AuthenticationPrincipal User user) {

				Convite convite = service.buscarPorIdEUsuario(id, user.getUsername());
				
					System.out.println(" O ID do convite é: -> "+ convite.getId());
				model.addAttribute("convite", convite);
				return "convite/convidar";
			}

			@PreAuthorize("hasAnyAuthority('ESPECIALISTA','CAMPANHA')")
			@PostMapping("/editar")
			public String editarConvite(Convite convite, RedirectAttributes attr, @AuthenticationPrincipal User user) {
				System.out.println("Já nesse caso, o id desse método é: "+convite.getId());
				
				service.editar(convite,user.getUsername());
				
				attr.addFlashAttribute("sucesso", "Seu convvite foi alterado com sucesso.");
				return "redirect:/convites/historico/liberados";
			}
	
	
			@PreAuthorize("hasAnyAuthority('ESPECIALISTA','CAMPANHA')")
			@ModelAttribute("congregacoes")
			public List<Congregacao>listaCongregaces(){
				return congregacaoService.buscarTodos();
			}
			
			@PreAuthorize("hasAnyAuthority('ESPECIALISTA','CAMPANHA')")
			@ModelAttribute("tipos")
			public List<Tipo>listarTipos(){
				return tipoService.buscarTodos();
			}
	
			@PreAuthorize("hasAnyAuthority('ESPECIALISTA','CAMPANHA')")
			@GetMapping("/excluir/{id}")
			public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
				service.remover(id);
				attr.addFlashAttribute("sucesso", "Registro excluído com sucesso.");
				return "redirect:/convites/";
			}
			
			
//			@PreAuthorize("hasAnyAuthority('ESPECIALISTA','CAMPANHA')")
//	@ModelAttribute("disponiveis/{id}")
//	public List<Congregacao>listaCongregacoesDisponiveis(@PathVariable("id") Long id){
//		return congregacaoService.buscarDisponiveis(id);
//	}
	
}
