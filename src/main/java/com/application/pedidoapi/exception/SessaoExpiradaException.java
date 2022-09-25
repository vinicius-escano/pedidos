package com.application.pedidoapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SessaoExpiradaException extends RuntimeException {

    public SessaoExpiradaException(String exception){
        super(exception);
    }
}
