package br.com.project.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ProductControllerExceptionHandler {

    @ExceptionHandler(ProductAlreadyExistException.class)
    public ResponseEntity handleProductAlreadyExist(ProductAlreadyExistException exception, WebRequest request) {
        var errorResponse = ErrorResponse.builder()
                .message(exception.getMessage())
                .build();

        return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        var errorResponse = exception.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> ErrorResponse.builder()
                        .field(fieldError.getField())
                        .message(fieldError.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());

        return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
    }

}