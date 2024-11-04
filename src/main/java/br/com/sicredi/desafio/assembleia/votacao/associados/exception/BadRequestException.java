package br.com.sicredi.desafio.assembleia.votacao.associados.exception;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}
