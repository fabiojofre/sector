package com.jofre.web.conversor;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.jofre.domain.Profissao;
import com.jofre.service.ProfissaoService;

@Component
public class ProfissoesConverter implements Converter<String[], Set<Profissao>> {

	@Autowired
	private ProfissaoService service;

	@Override
	public Set<Profissao> convert(String[] titulos) {
		
		Set<Profissao> profissoes = new HashSet<>();
		if (titulos != null && titulos.length > 0) {
			profissoes.addAll(service.buscarPorTitulos(titulos));			
		}
		return profissoes;
	}
}
