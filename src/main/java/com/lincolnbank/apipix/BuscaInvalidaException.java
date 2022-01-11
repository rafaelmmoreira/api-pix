package com.lincolnbank.apipix;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason="Busca deve conter o campo id ou o campo status.")
public class BuscaInvalidaException extends RuntimeException {
    
}
