
package br.com.desafio.livraria.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.desafio.livraria.dto.AssuntoDto;
import br.com.desafio.livraria.dto.LivroDto;
import br.com.desafio.livraria.entity.Assunto;
import br.com.desafio.livraria.entity.Livro;
import br.com.desafio.livraria.entity.LivroAssunto;
import br.com.desafio.livraria.entity.LivroAssuntoId;
import br.com.desafio.livraria.repository.AssuntoRepository;
import br.com.desafio.livraria.repository.LivroRepository;
import br.com.desafio.livraria.repository.VinculoLivroAssuntoRepository;
import br.com.desafio.livraria.validation.AssuntoValidador;
import br.com.desafio.livraria.validation.LivroValidador;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class VinculoLivroAssuntoService {

    @Autowired
    private LivroRepository livroRepository;
    
    @Autowired
    private AssuntoRepository assuntoRepository;
    
    @Autowired
    private VinculoLivroAssuntoRepository vinculoLivroAssuntoRepository;
    
    @Autowired
    private LivroValidador livroValidador;
    
    @Autowired
    private AssuntoValidador assuntoValidador;

    public LivroDto vincularLivroAssunto(Integer livroId, Integer assuntoId) {
    	Livro livro = livroValidador.validarLivroExistePeloId(livroId);
        Assunto assunto = assuntoValidador.validarAssuntoExistePeloId(assuntoId);
        
        vinculoLivroAssuntoRepository.save(LivroAssunto.builder()
        		.livroAssuntoId(new LivroAssuntoId(livro.getCodLivro(), assunto.getCodAssunto()))
        		.build());
        return retornarAssuntosLivro(livro.toDTO());
    }
    
    public LivroDto deletarVinculoAssunto(Integer livroId, Integer assuntoId) {
    	Livro livro = livroValidador.validarLivroExistePeloId(livroId);
        Assunto assunto = assuntoValidador.validarAssuntoExistePeloId(assuntoId);
        
        vinculoLivroAssuntoRepository.deleteById(new LivroAssuntoId(livro.getCodLivro(), assunto.getCodAssunto()));
        return retornarAssuntosLivro(livro.toDTO());
    }

    public LivroDto retornarAssuntosLivro(LivroDto livroDto) {
    	List<Assunto> lsAssuntos = assuntoRepository.findAllbyIdLivro(livroDto.getCodLivro());
		livroDto.setAssuntos(lsAssuntos.stream()
	        .map(Assunto::toDTO)
	        .toList());
		return livroDto;
    }
    
    public AssuntoDto retornarLivrosAssunto(AssuntoDto assuntoDto) {
    	List<Livro> lsLivro = livroRepository.findAllbyIdAssunto(assuntoDto.getCodAssunto());
    	assuntoDto.setLivros(lsLivro.stream()
	        .map(Livro::toResponseDTO)
	        .toList());
		return assuntoDto;
    }
}
