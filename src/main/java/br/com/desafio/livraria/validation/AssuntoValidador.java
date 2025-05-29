package br.com.desafio.livraria.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.desafio.livraria.dto.AssuntoDto;
import br.com.desafio.livraria.entity.Assunto;
import br.com.desafio.livraria.exception.RecursoDuplicadoException;
import br.com.desafio.livraria.exception.RecursoNaoEncontradoException;
import br.com.desafio.livraria.repository.AssuntoRepository;

@Component
public class AssuntoValidador {
	
	@Autowired
    private AssuntoRepository assuntoRepository;
	
	public void validarAssuntoRepetido(AssuntoDto assuntoDto) {
		if (assuntoRepository.existsByDescricao(assuntoDto.getDescricao()))
			throw new RecursoDuplicadoException("erro.validador.assunto.jaCadastrado");
	}
    
	public Assunto validarAssuntoExistePeloId(Integer id) {
    	return assuntoRepository.findById(id)
        	.orElseThrow(() -> new RecursoNaoEncontradoException("erro.validador.assunto.naoEncontrado", id));
	}

}
