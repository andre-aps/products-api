package br.com.project.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProductAlreadyExistException extends Exception {

    public ProductAlreadyExistException(String name) {
        super(String.format("Product with name %s already exist in the system.", name));
    }

}
