package br.com.sicredi.desafio.assembleia.votacao.associados.web;

import br.com.sicredi.desafio.assembleia.votacao.associados.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BadRequestExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    private ResponseEntity<Object> handleBadRequest(BadRequestException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }
}