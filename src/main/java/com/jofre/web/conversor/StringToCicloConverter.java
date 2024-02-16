package com.jofre.web.conversor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.jofre.domain.Ciclo;
import com.jofre.service.CicloService;

@Component
public class StringToCicloConverter implements Converter<String, Ciclo> {

	@Autowired
	 private  CicloService cicloService;
	
	
	@Override
	public Ciclo convert(String text) {
		if(text.isEmpty()) {
			return null;
		}
		Long id = Long.valueOf(text);
		return cicloService.buscarPorId(id);
	}

}
