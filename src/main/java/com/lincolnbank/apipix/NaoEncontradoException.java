package com.lincolnbank.apipix;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason="Pagamento não encontrado.")
public class NaoEncontradoException extends RuntimeException {
    
}