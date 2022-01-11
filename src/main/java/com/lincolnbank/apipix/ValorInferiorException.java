package com.lincolnbank.apipix;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason="Valor baixo demais para o período de recorrência selecionado.")
public class ValorInferiorException extends RuntimeException {
    
}