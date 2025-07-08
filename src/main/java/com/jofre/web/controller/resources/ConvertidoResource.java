package com.jofre.web.controller.resources;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jofre.domain.Convertido;
import com.jofre.service.ConvertidoService;

@RestController
@RequestMapping(value="/conversoes")
public class ConvertidoResource {

	@Autowired
	private ConvertidoService service;


	@RequestMapping(value="/{data}", method = RequestMethod.GET)
	public ResponseEntity<List<Convertido>> findAllData(@PathVariable @DateTimeFormat(iso = ISO.DATE) LocalDate data){
		List<Convertido> list = service.findAllByDate(data);
		return ResponseEntity.ok().body(list);
	}
	
}
