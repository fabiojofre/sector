
package com.jofre.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jofre.domain.Congregacao;
import com.jofre.domain.Convertido;
import com.jofre.domain.Pessoa;
import com.jofre.service.CongregacaoService;
import com.jofre.service.ConvertidoService;
import com.jofre.service.PessoaService;

@Controller
@RequestMapping("convertidos")
public class ConvertidoController {

	@Autowired
	private ConvertidoService service;

	@Autowired
	private PessoaService pessoaService;

	@Autowired
	private CongregacaoService congregacaoService;

	@GetMapping({ "", "/" })
	public String abrir(Convertido convertido) {

		return "convertido/convertido";

	}
	
	@PostMapping({  "/salvar" })
	public String salvar(Convertido convertido, RedirectAttributes attr, @AuthenticationPrincipal User user) {
		Pessoa pessoa = pessoaService.buscarPorUsuarioEmail(user.getUsername());
		String nome = convertido.getCongregacao().getNome();
		Congregacao congregacao = congregacaoService.buscarCongregacaoPorNome(nome);
		convertido.setCongregacao(congregacao);
		convertido.setPessoa(pessoa);
		service.salvar(convertido);
		attr.addFlashAttribute("sucesso", "Convertido cadastrado com sucesso.");
		return "redirect:/convertidos/";

	}
}
