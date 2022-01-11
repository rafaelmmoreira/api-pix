package com.lincolnbank.apipix;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason="Datas devem estar no formato \"AAAA-MM-DD\".")
public class DataInvalidaException extends RuntimeException {
    
}
