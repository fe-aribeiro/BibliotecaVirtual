package br.com.desafio.livraria.exception;

public class RecursoDuplicadoException extends RuntimeException {
	private static final long serialVersionUID = -7902122118597447793L;

	public RecursoDuplicadoException(String message) {
        super(message);
    }
}

