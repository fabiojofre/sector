package com.jofre.web.conversor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.jofre.domain.Congregacao;
import com.jofre.service.CongregacaoService;

@Component
public class StringToCongregacaoConverter implements Converter<String, Congregacao> {

	@Autowired
	 private  CongregacaoService congregacaoService;
	
	
	@Override
	public Congregacao convert(String text) {
		if(text.isEmpty()) {
			return null;
		}
		Long id = Long.valueOf(text);
		return congregacaoService.buscarPorId(id);
	}

}
