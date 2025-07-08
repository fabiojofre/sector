package com.jofre.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jofre.domain.CargoEc;
import com.jofre.domain.Congregacao;
import com.jofre.domain.Obreiro;
import com.jofre.domain.Pessoa;
import com.jofre.service.CargoEcService;
import com.jofre.service.CongregacaoService;
import com.jofre.service.ObreiroService;
import com.jofre.service.PessoaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("trabalhadores")
public class TrabalhadorController {

	@Autowired
	private ObreiroService service;
	@Autowired
	private CargoEcService CargoEcService;
	@Autowired
	private CongregacaoService congregacaoService;
	
	@GetMapping({ "", "/" })
	public String abrir(Obreiro obreiro) {

		return "trabalhador/trabalhador";
	}
	
	
	// salvar obreiro
		
		@PostMapping("/salvar")
		public String salvar(@Valid Obreiro obreiro, BindingResult result, RedirectAttributes attr) {
			
			if(result.hasErrors()) {
				return "trabalhador/trabalhador";
				
			}if(service.buscarPorCartao(obreiro.getCartaoMembro()).size()>0) {
				Obreiro con = new Obreiro();
				con =  service.buscarPorCartao(obreiro.getCartaoMembro()).get(0); // retorna o primeiro obreiro
				attr.addFlashAttribute("falha","Cartão nº "+obreiro.getCartaoMembro()+ 
						", já cadastrado para o obreiro "+con.getNome()+" na congregação em "+con.getCongregacao().getNome()+". ");
				return "redirect:/trabalhadores/";
				
			}else {
				obreiro.setBloqueado(false);
				service.salvar(obreiro);
				attr.addFlashAttribute("sucesso", "Operação realizada com sucesso!");
				return "redirect:/trabalhadores/";
			}
		}
	
		
		@ModelAttribute("congregacoes")
		public List<Congregacao>listaCongregaces(){
			return congregacaoService.buscarOrdem();
		}
		
		@ModelAttribute("cargos_ec")
		public List<CargoEc>listaCargoss(){
			return CargoEcService.buscarTodos();
		}
		
		// localizar obreiro pelo id e envia-lo para a pagina de cadastro
		@PreAuthorize("hasAnyAuthority('ESPECIALISTA','ASSISTENTE')")
		@GetMapping("/editar/{id}")
		public String preEditarcObreiro(@PathVariable("id") Long id, ModelMap model) {

			Obreiro obreiro = service.buscarPorIdEUsuario(id);
//				System.out.println(" O ID do convertido é: -> "+ obreiro.getId());
			model.addAttribute("trabalhador", obreiro);
			return "trabalhador/trabalhador";
		}
		@PreAuthorize("hasAnyAuthority('ESPECIALISTA','ASSISTENTE')")
		@PostMapping("/editar")
		public String editarObreiros(Obreiro obreiro, RedirectAttributes attr) {
			System.out.println("Já nesse caso, o id desse método é: "+obreiro.getId());
			
			service.editar(obreiro);
			
			attr.addFlashAttribute("sucesso", "Membro alterado com sucesso.");
			return "redirect:/obreiros/historico/obreiros";
		}

		@PreAuthorize("hasAnyAuthority('ESPECIALISTA','ASSISTENTE')")
		@GetMapping("/excluir/{id}")
		public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
			service.remover(id);
			attr.addFlashAttribute("sucesso", "Obreiro excluído com sucesso.");
			return "redirect:/obreiros/historico/obreiros";
		}
		
}
