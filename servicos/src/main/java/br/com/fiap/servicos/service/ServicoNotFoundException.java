package br.com.fiap.servicos.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ServicoNotFoundException extends RuntimeException {

    public ServicoNotFoundException(String message) {
        super(message);
    }
}
