package com.jofre.web.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.jofre.domain.Pessoa;
import com.jofre.service.PessoaService;

public class PessoaValidator implements Validator {

    PessoaService service;

    public PessoaValidator(PessoaService service) {
        this.service = service;
    }

    @Override
    public boolean supports(Class<?> clazz) {

        return Pessoa.class.equals(clazz);

    }

    @Override
    public void validate(Object object, Errors errors) {

        Pessoa entity = (Pessoa) object;

        if (entity.getId() != null) {

            if (1 != 0) {

                errors.rejectValue("nome", "Existe.nome");

            }

        }

    }
}
