package br.com.desafio.livraria.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.desafio.livraria.dto.AutorDto;
import br.com.desafio.livraria.entity.Autor;
import br.com.desafio.livraria.exception.RecursoDuplicadoException;
import br.com.desafio.livraria.exception.RecursoNaoEncontradoException;
import br.com.desafio.livraria.repository.AutorRepository;

@Component
public class AutorValidador {
	
	@Autowired
    private AutorRepository autorRepository;
	
	public void validarAutorRepetido(AutorDto autorDto) {
		if (autorRepository.existsByNome(autorDto.getNome()))
			throw new RecursoDuplicadoException("erro.validador.autor.jaCadastrado");
	}
    
	public Autor validarAutorExistePeloId(Integer id) {
    	return autorRepository.findById(id)
        	.orElseThrow(() -> new RecursoNaoEncontradoException("erro.validador.autor.naoEncontrado", id));
	}

}
