package br.com.desafio.livraria.exception;

public class RecursoNaoEncontradoException extends RuntimeException {
	private static final long serialVersionUID = 3973955736431098067L;

	private final Integer id;

    public RecursoNaoEncontradoException(String mensagem, Integer id) {
        super(mensagem);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}

