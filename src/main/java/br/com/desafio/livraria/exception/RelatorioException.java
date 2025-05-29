package br.com.desafio.livraria.exception;

public class RelatorioException extends RuntimeException {
	private static final long serialVersionUID = 3973955736431098067L;

    public RelatorioException(String mensagem, Throwable t) {
        super(mensagem, t);
    }
}

