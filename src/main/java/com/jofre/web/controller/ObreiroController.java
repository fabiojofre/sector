package com.jofre.web.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
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
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("obreiros")
public class ObreiroController {

	@Autowired
	private ObreiroService service;
	@Autowired
	private PessoaService pessoaService;
	@Autowired
	private CargoEcService CargoEcService;
	@Autowired
	private CongregacaoService congregacaoService;
	
	@GetMapping({ "", "/" })
	public String abrir(Obreiro obreiro) {

		return "obreiro/obreiro";
	}
	
	
	// salvar obreiro
		
		@PostMapping("/salvar")
		public String salvar(@Valid Obreiro obreiro, BindingResult result, RedirectAttributes attr) {
			
			if(result.hasErrors()) {
				return "obreiro/obreiro";
				
			}if(service.buscarPorCartao(obreiro.getCartaoMembro()).size()>0) {
				Obreiro con = new Obreiro();
				con =  service.buscarPorCartao(obreiro.getCartaoMembro()).get(0); // retorna o primeiro obreiro
				attr.addFlashAttribute("falha","Cartão nº "+obreiro.getCartaoMembro()+ 
						", já cadastrado para o obreiro "+con.getNome()+" na congregação em "+con.getCongregacao().getNome()+". ");
				return "redirect:/obreiros/";
				
			}else {
				
				service.salvar(obreiro);
				attr.addFlashAttribute("sucesso", "Operação realizada com sucesso!");
				return "redirect:/obreiros/";
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
		
		@PreAuthorize("hasAnyAuthority('ESPECIALISTA','ASSISTENTE')")
		@GetMapping("/listar")
		public String listar(ModelMap model) {
			model.addAttribute("obreiros", service.buscarTodos());
			return "obreiro/lista";
		}
		
		@GetMapping("/editar/{id}")
		public String preEditar(@PathVariable("id") Long id, ModelMap model) {
			model.addAttribute("obreiro", service.buscarPorId(id));
			return "obreiro/obreiro";
		}
		
		@PostMapping("/editar")
		public String editar(@Valid Obreiro obreiro, BindingResult result, RedirectAttributes attr) {
			if(result.hasErrors()) {
				return "obreiro/obreiro";
			}
			service.editar(obreiro);
			attr.addFlashAttribute("success","Obreiro atualizado com sucesso.");
			return "redirect:/obreiros/listar";
		}
		
		
		@GetMapping("/excluir/{id}")
		public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
//			if(obreiroservice.ObreiroTemMovimentos(id)) {
//				attr.addFlashAttribute("fail","Ponto de Pregação não excluido. Existem movimentações vinculadas a ele.");
//			}else {
				service.remover(id);
				attr.addFlashAttribute("success","Obreiro excluido com sucesso.");
//			}
			return "redirect:/obreiros/listar";
		}
		
		@GetMapping("/buscar/nome")
		public String getPorNome(@RequestParam("nome") String nome, ModelMap model) {
			model.addAttribute("obreiros", service.buscarPorNome(nome));
			return "obreiro/lista";
		}
		
		@GetMapping("/buscar/congregacao")
		public String getPorCongregacao(@RequestParam("id") Long id, ModelMap model) {
			model.addAttribute("obreiros", service.buscarPorCongregacao(id));
			return "obreiro/lista";
		}
		@GetMapping("/buscar/cargo")
		public String getPorCargo(@RequestParam("id") Long id, ModelMap model) {
			model.addAttribute("obreiros", service.buscarPorCargo(id));
			return "obreiro/lista";
		}
		@GetMapping("/buscar/cartao")
		public String getPorCartao(@RequestParam("cartaoMembro") Long cartaoMembro, ModelMap model) {
			model.addAttribute("obreiros", service.buscarPorCartao(cartaoMembro));
			return "obreiro/lista";
		}
		@GetMapping("/buscar/aniversemana")
		public String getPorDataNiver(@RequestParam("data")@DateTimeFormat(iso = ISO.DATE) LocalDate data, ModelMap model) {
			model.addAttribute("obreiros", service.buscarAniversariantes(data));
			return "obreiro/lista";
		}

		@GetMapping("/buscar/data")
		public String getPorData(@RequestParam("data")@DateTimeFormat(iso = ISO.DATE) LocalDate data, ModelMap model) {
			model.addAttribute("obreiros", service.buscarPorData(data));
			return "obreiro/lista";
		}
		
		
//		@GetMapping("/buscar/data")
//		public String getPorData(@RequestParam("data")@DateTimeFormat(iso = ISO.DATE) LocalDate data, ModelMap model) {
//			model.addAttribute("obreiros", service.buscarPorData(data));
//			return "obreiro/lista";
//		}
//
//		@GetMapping("/buscar/datacongregacao")
//		public String getPorDataCongregacao(@RequestParam("data")@DateTimeFormat(iso = ISO.DATE) LocalDate data, @RequestParam("congregacao") Integer congregacao, ModelMap model) {
//			model.addAttribute("obreiros", service.buscarPorDataCongregacao(data,congregacao));
//			return "obreiro/lista2";
//		}
		
}
