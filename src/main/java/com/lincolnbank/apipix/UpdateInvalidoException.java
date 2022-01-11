package com.lincolnbank.apipix;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason="Updates devem conter o ID do pagamento original.")
public class UpdateInvalidoException extends RuntimeException {
    
}
