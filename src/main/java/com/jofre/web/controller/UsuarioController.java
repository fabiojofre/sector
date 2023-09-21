package com.jofre.web.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jofre.domain.Especialista;
import com.jofre.domain.Perfil;
import com.jofre.domain.PerfilTipo;
import com.jofre.domain.Usuario;
import com.jofre.service.EspecialistaService;
import com.jofre.service.UsuarioService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("u")
public class UsuarioController {
	
	@Autowired
	private UsuarioService service;
	@Autowired
	private EspecialistaService especialistaService;
	
    // abrir cadastro de usuarios (especialista/admin/pessoa)
    @GetMapping("/novo/cadastro/usuario")
    public String cadastroPorAdminParaAdminespecialistaPessoa(Usuario usuario) {

    	return "usuario/cadastro";
    }
    
    // abrir lista de usuarios
    @GetMapping("/lista")
    public String listarUsuarios() {

        return "usuario/lista";
    }  

    // listar usuarios na datatables
    @GetMapping("/datatables/server/usuarios")
    public ResponseEntity<?> listarUsuariosDatatables(HttpServletRequest request) {

        return ResponseEntity.ok(service.buscarTodos(request));
    } 
    
    // salvar cadastro de usuarios por administrador
    @PostMapping("/cadastro/salvar")
    public String salvarUsuarios(Usuario usuario, RedirectAttributes attr) {
    	List<Perfil> perfis = usuario.getPerfis();
    	if (perfis.size() > 2 || 
    			perfis.containsAll(Arrays.asList(new Perfil(1L), new Perfil(3L))) ||
    			perfis.containsAll(Arrays.asList(new Perfil(2L), new Perfil(3L)))) {
    		attr.addFlashAttribute("falha", "Pessoa não pode ser Admin e/ou Especialista.");
    		attr.addFlashAttribute("usuario", usuario);
    	} else {
    		try {
    			service.salvarUsuario(usuario); 
    			attr.addFlashAttribute("sucesso", "Operação realizada com sucesso!");
    		} catch (DataIntegrityViolationException ex) {
    			attr.addFlashAttribute("falha", "Cadastro não realizado, email já existente.");
			}
    	}
    	return "redirect:/u/novo/cadastro/usuario";
    }
    
    // pre edicao de credenciais de usuarios
    @GetMapping("/editar/credenciais/usuario/{id}")
    public ModelAndView preEditarCredenciais(@PathVariable("id") Long id) {

        return new ModelAndView("usuario/cadastro", "usuario", service.buscarPorId(id));
    }    
    
    // pre edicao de cadastro de usuarios
    @GetMapping("/editar/dados/usuario/{id}/perfis/{perfis}")
    public ModelAndView preEditarCadastroDadosPessoais(@PathVariable("id") Long usuarioId, 
    												   @PathVariable("perfis") Long[] perfisId) {
    	Usuario us = service.buscarPorIdEPerfis(usuarioId, perfisId);
    	
    	if (us.getPerfis().contains(new Perfil(PerfilTipo.ADMIN.getCod())) &&
    		!us.getPerfis().contains(new Perfil(PerfilTipo.ESPECIALISTA.getCod())) ) {
    		
    		return new ModelAndView("usuario/cadastro", "usuario", us);
    	} else if (us.getPerfis().contains(new Perfil(PerfilTipo.ESPECIALISTA.getCod()))) {
    		
    		Especialista especialista = especialistaService.buscarPorUsuarioId(usuarioId);
    		return especialista.hasNotId()
    				? new ModelAndView("especialista/cadastro", "ESPECIALISTA", new Especialista(new Usuario(usuarioId)))
    				: new ModelAndView("especialista/cadastro", "ESPECIALISTA", especialista);
    	} else if (us.getPerfis().contains(new Perfil(PerfilTipo.PESSOA.getCod()))) {
    		ModelAndView model = new ModelAndView("error");
    		model.addObject("status", 403);
    		model.addObject("error", "Área Restrita");
    		model.addObject("message", "Os dados de pessoas são restritos a ele.");
    		return model;
    	}
    	
        return new ModelAndView("redirect:/u/lista");
    }  
    
    @GetMapping("/editar/senha")
    public String abrirEditarSenha() {
    	
    	return "usuario/editar-senha";
    }
    
    @PostMapping("/confirmar/senha")
    public String editarSenha(@RequestParam("senha1") String s1, @RequestParam("senha2") String s2, 
    						  @RequestParam("senha3") String s3, @AuthenticationPrincipal User user,
    						  RedirectAttributes attr) {
    	
    	if (!s1.equals(s2)) {
    		attr.addFlashAttribute("falha", "Senhas não conferem, tente novamente");
    		return "redirect:/u/editar/senha";
    	}
    	
    	Usuario u = service.buscarPorEmail(user.getUsername());
    	if(!UsuarioService.isSenhaCorreta(s3, u.getSenha())) {
    		attr.addFlashAttribute("falha", "Senha atual não confere, tente novamente");
    		return "redirect:/u/editar/senha";
    	}
    		
    	service.alterarSenha(u, s1);
    	attr.addFlashAttribute("sucesso", "Senha alterada com sucesso.");
    	return "redirect:/u/editar/senha";
    }
    
    // abrir página de novo cadastro de pessoa
    @GetMapping("/novo/cadastro")
    public String novoCadastro(Usuario usuario) {

        return "cadastrar-se";
    } 
    
    // pagina de resposta do cadatro de pessoa
    @GetMapping("/cadastro/realizado")
    public String cadastroRealizado() {

        return "fragments/mensagem";
    }
    
    // rebece o form da página cadastrar-se
    @PostMapping("/cadastro/pessoa/salvar")
    public String salvarCadastroPessoa(Usuario usuario, BindingResult result) throws MessagingException {
    	try {
    	service.salvarCadastroPessoa(usuario);
    	} catch (DataIntegrityViolationException ex) {
    		result.reject("email", "Ops... Este e-mail já existe na base de dados.");
    		return "cadastrar-se";
    	}
    	return "redirect:/u/cadastro/realizado";
    }
    
    // recebe a requisicao de confirmacao de cadastro
    @GetMapping("/confirmacao/cadastro")
    public String respostaConfirmacaoCadastroPessoa(@RequestParam("codigo") String codigo, 
    												  RedirectAttributes attr) {    	
        service.ativarCadastroPessoa(codigo);
        attr.addFlashAttribute("alerta", "sucesso");
        attr.addFlashAttribute("titulo", "Cadastro Ativado!");
        attr.addFlashAttribute("texto", "Parabéns, seu cadastro está ativo.");
        attr.addFlashAttribute("subtexto", "Singa com seu login/senha");
    	return "redirect:/login";
    } 
    
    // abre a pagina de pedido de redefinicao de senha
    @GetMapping("/p/redefinir/senha")
    public String pedidoRedefinirSenha() {
    	 
    	return "usuario/pedido-recuperar-senha";
    }
    
    // form de pedido de recuperar senha
    @GetMapping("/p/recuperar/senha")
    public String redefinirSenha(String email, ModelMap model) throws MessagingException {
    	service.pedidoRedefinicaoDeSenha(email);
    	model.addAttribute("sucesso", "Em instantes você reberá um e-mail para "
    			+ "prosseguir com a redefinição de sua senha.");
    	model.addAttribute("usuario", new Usuario(email));
    	return "usuario/recuperar-senha";
    }
    
    // salvar a nova senha via recuperacao de senha
    @PostMapping("/p/nova/senha")
    public String confirmacaoDeRedefinicaoDeSenha(Usuario usuario, ModelMap model) {
    	Usuario u = service.buscarPorEmail(usuario.getEmail());
    	if (!usuario.getCodigoVerificador().equals(u.getCodigoVerificador())) {
    		model.addAttribute("falha", "Código verificador não confere.");
    		return "usuario/recuperar-senha";
    	}
    	u.setCodigoVerificador(null);
    	service.alterarSenha(u, usuario.getSenha());
    	model.addAttribute("alerta", "sucesso");
    	model.addAttribute("titulo", "Senha redefinida!");
    	model.addAttribute("texto", "Você já pode logar no sistema.");
    	return "login";
    }
}
