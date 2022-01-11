package com.lincolnbank.apipix;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason="Período longo demais para a recorrência selecionada.")
public class PeriodoSuperiorException extends RuntimeException {
    
}