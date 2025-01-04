package com.jofre.web.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

import com.jofre.domain.Cargo;
import com.jofre.domain.Congregacao;
import com.jofre.domain.Congregado;
import com.jofre.domain.EstadoCivil;
import com.jofre.domain.Grau;
import com.jofre.domain.Limitacao;
import com.jofre.domain.Origem;
import com.jofre.domain.PerfilTipo;
import com.jofre.domain.Pessoa;
import com.jofre.domain.Profissao;
import com.jofre.service.CargoService;
import com.jofre.service.CongregacaoService;
import com.jofre.service.CongregadoService;
import com.jofre.service.EstadoCivilService;
import com.jofre.service.GrauService;
import com.jofre.service.LimitacaoService;
import com.jofre.service.OrigemService;
import com.jofre.service.PessoaService;
import com.jofre.service.ProfissaoService;
import com.jofre.service.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("congregados")
public class CongregadoController {

	
	@Autowired
	private CongregadoService service;
	
	@Autowired
	private CongregacaoService congregacaoService;
	
	@Autowired
	private EstadoCivilService estadoCivilService;
	
	@Autowired
	private OrigemService origemService;
	
	@Autowired
	private GrauService grauService;
	
	@Autowired
	private LimitacaoService limitacaoService;
	
	@Autowired
	private ProfissaoService profissaoService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	
	@Autowired
	private CargoService cargoService;
	
	@Autowired
	private PessoaService pessoaService;
	
	@PreAuthorize("hasAnyAuthority('ESPECIALISTA','ASSISTENTE')")
	@GetMapping({"", "/"})
	public String abrir(Congregado congregado) {
		return "congregado/congregado";
	}
	
//	@PreAuthorize("hasAuthority('ESPECIALISTA')")
//	@GetMapping({ "/convidar"})
//	public String abrirConvidar(Congregado congregado) {
//		return "congregado/congregadoEditar";
//	}
	
	// salvar congregado
	@PreAuthorize("hasAnyAuthority('ESPECIALISTA','ASSISTENTE')")
	@PostMapping("/salvar")
	public String salvar(@Valid Congregado congregado, BindingResult result, RedirectAttributes attr, @AuthenticationPrincipal User user) {
		
		if(result.hasErrors()) {
			return "congregado/congregado";
			
		}if(service.buscarPorCartao(congregado.getCartaoMembro()).size()>0) {
			Congregado con = new Congregado();
			con =  service.buscarPorCartao(congregado.getCartaoMembro()).get(0); // retorna o primeiro congregado
			attr.addFlashAttribute("falha","Cartão nº "+congregado.getCartaoMembro()+ 
					", já cadastrado para o membro "+con.getNome()+" na congregação em "+con.getCongregacao().getNome()+". ");
			return "redirect:/congregados/";
			
		}if(service.buscarPorCPF(congregado.getCpf()).size()> 0) {
			Congregado con = new Congregado();
			con =  service.buscarPorCPF(congregado.getCpf()).get(0); 
			attr.addFlashAttribute("falha","CPF já cadastrado para o membro: "+con.getNome()+" na congregação em "+con.getCongregacao().getNome()+". ");
			return "redirect:/congregados/";
			
		}else {
		
			Pessoa pessoa = pessoaService.buscarPorUsuarioEmail(user.getUsername());
			Long id_congreg = pessoa.getCongregacao().getId();
			Congregacao congregacao = congregacaoService.buscarPorId(id_congreg);
						
			congregado.setUsuariocadastro(usuarioService.buscarPorEmail(user.getUsername()));
			congregado.setDataCadastro(LocalDate.now());
			congregado.setCongregacao(congregacao);
		
			service.salvar(congregado);
			attr.addFlashAttribute("sucesso", "Operação realizada com sucesso!");
			return "redirect:/congregados/";
		}
	}
	
	
	// salvar congregado
		@PreAuthorize("hasAnyAuthority('ESPECIALISTA','ASSISTENTE')")
		@PostMapping("/salvar2")
		public String save(@Valid Congregado congregado, BindingResult result, RedirectAttributes attr, @AuthenticationPrincipal User user) {
			if(result.hasErrors()) {
				return "congregado/congregado";
				
			}if(service.buscarPorCartao(congregado.getCartaoMembro()).size()>0) {
				attr.addFlashAttribute("falha","Cartão nº "+congregado.getCartaoMembro()+ 
						", já cadastrado para o membro "+congregado.getNome()+" na congregação em "+congregado.getCongregacao().getNome()+". ");
				return "redirect:/congregados/";
				
			}if(service.buscarPorCPF(congregado.getCpf()).size()> 0) {
				attr.addFlashAttribute("falha","CPF já cadastrado para o membro: "+congregado.getNome()+" na congregação em "+congregado.getCongregacao().getNome()+". ");
				return "redirect:/congregados/";
				
			}else {
			congregado.setUsuariocadastro(usuarioService.buscarPorEmail(user.getUsername()));
			congregado.setDataCadastro(LocalDate.now());
			
			service.salvar(congregado);
			attr.addFlashAttribute("sucesso", "Operação realizada com sucesso!");
				return "redirect:/congregados/";
			}
		}
	
	
	//listagem congregado
	
	@PreAuthorize("hasAnyAuthority('ESPECIALISTA','ASSISTENTE')")
	@GetMapping("/historico/congregados")
	public String historicoCongregados() {

		return "congregado/historico-congregado";
	}
	
	@PreAuthorize("hasAnyAuthority('ESPECIALISTA','ASSISTENTE')")
	@GetMapping("/datatables/server/historico")
	public ResponseEntity<?> getBuscarCongregados(HttpServletRequest request, @AuthenticationPrincipal User user) {
		if (user.getAuthorities().contains(new SimpleGrantedAuthority(PerfilTipo.ASSISTENTE.getDesc()))) {
			Pessoa pessoa = pessoaService.buscarPorUsuarioEmail(user.getUsername());
						
		return ResponseEntity.ok(service.buscarCongregadosPorCongregacao(pessoa.getCongregacao().getId(),request));
		}
		if (user.getAuthorities().contains(new SimpleGrantedAuthority(PerfilTipo.ESPECIALISTA.getDesc()))) {
			return ResponseEntity.ok(service.buscarCongregados(request));
		}
		else {
			return ResponseEntity.badRequest().build();
		}
			
	}
	
	
	
			// localizar congregado pelo id e envia-lo para a pagina de cadastro
			@PreAuthorize("hasAnyAuthority('ESPECIALISTA','ASSISTENTE')")
			@GetMapping("/editar/{id}")
			public String preEditarcCongregado(@PathVariable("id") Long id, ModelMap model,
					@AuthenticationPrincipal User user) {

				Congregado congregado = service.buscarPorIdEUsuario(id,user.getUsername());
//					System.out.println(" O ID do convertido é: -> "+ congregado.getId());
				model.addAttribute("congregado", congregado);
				return "congregado/congregado";
			}
			@PreAuthorize("hasAnyAuthority('ESPECIALISTA','ASSISTENTE')")
			@PostMapping("/editar")
			public String editarCongregados(Congregado congregado, RedirectAttributes attr, @AuthenticationPrincipal User user) {
				System.out.println("Já nesse caso, o id desse método é: "+congregado.getId());
				
				service.editar(congregado,user.getUsername());
				
				attr.addFlashAttribute("sucesso", "Membro alterado com sucesso.");
				return "redirect:/congregados/historico/congregados";
			}

			
			
			@PreAuthorize("hasAnyAuthority('ESPECIALISTA','ASSISTENTE')")
			@GetMapping("/excluir/{id}")
			public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
				service.remover(id);
				attr.addFlashAttribute("sucesso", "Congregado excluído com sucesso.");
				return "redirect:/congregados/historico/congregados";
			}
	
	
			@PreAuthorize("hasAnyAuthority('ESPECIALISTA','ASSISTENTE')")
			@ModelAttribute("congregacoes")
			public List<Congregacao>listaCongregaces(){
				return congregacaoService.buscarOrdem();
			}
			
			@PreAuthorize("hasAnyAuthority('ESPECIALISTA','ASSISTENTE')")
			@ModelAttribute("origens")
			public List<Origem>listarOrigems(){
				return origemService.buscarTodos();
			}
			
			@PreAuthorize("hasAnyAuthority('ESPECIALISTA','ASSISTENTE')")
			@ModelAttribute("limitacoes")
			public List<Limitacao>listarLimitacoes(){
				return limitacaoService.buscarTodos();
			}
	
			@PreAuthorize("hasAnyAuthority('ESPECIALISTA','ASSISTENTE')")
			@ModelAttribute("profissoes")
			public List<Profissao>listarProficoes(){
				return profissaoService.buscarTodos();
			}
			
			@PreAuthorize("hasAnyAuthority('ESPECIALISTA','ASSISTENTE')")
			@ModelAttribute("estadoscivis")
			public List<EstadoCivil>listarEstadoCivil(){
				return estadoCivilService.buscarTodos();
			}
			
			@PreAuthorize("hasAnyAuthority('ESPECIALISTA','ASSISTENTE')")
			@ModelAttribute("graus")
			public List<Grau>listarGraus(){
				return grauService.buscarTodos();
			} 
			@PreAuthorize("hasAnyAuthority('ESPECIALISTA','ASSISTENTE')")
			@ModelAttribute("cargos")
			public List<Cargo>listarCargos(){
				return cargoService.buscarTodos();
			}
	
}
