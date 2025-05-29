package br.com.desafio.livraria.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.desafio.livraria.dto.LivroDto;
import br.com.desafio.livraria.entity.Livro;
import br.com.desafio.livraria.exception.RecursoDuplicadoException;
import br.com.desafio.livraria.exception.RecursoNaoEncontradoException;
import br.com.desafio.livraria.repository.LivroRepository;

@Component
public class LivroValidador {
	
	@Autowired
    private LivroRepository livroRepository;
	
	public void validarLivroRepetido(LivroDto livroDto) {
		if (livroRepository.existsByTituloAndEditoraAndAnoPublicacaoAndEdicao(livroDto.getTitulo(), livroDto.getEditora(), livroDto.getAnoPublicacao(), livroDto.getEdicao()))
			throw new RecursoDuplicadoException("erro.validador.livro.jaCadastrado");
	}
    
	public Livro validarLivroExistePeloId(Integer id) {
    	return livroRepository.findById(id)
        	.orElseThrow(() -> new RecursoNaoEncontradoException("erro.validador.livro.naoEncontrado", id));
	}

}
