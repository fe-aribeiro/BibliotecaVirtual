
package br.com.desafio.livraria.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.desafio.livraria.dto.AutorDto;
import br.com.desafio.livraria.dto.LivroDto;
import br.com.desafio.livraria.entity.Autor;
import br.com.desafio.livraria.entity.Livro;
import br.com.desafio.livraria.entity.LivroAutor;
import br.com.desafio.livraria.entity.LivroAutorId;
import br.com.desafio.livraria.repository.AutorRepository;
import br.com.desafio.livraria.repository.LivroRepository;
import br.com.desafio.livraria.repository.VinculoLivroAutorRepository;
import br.com.desafio.livraria.validation.AutorValidador;
import br.com.desafio.livraria.validation.LivroValidador;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class VinculoLivroAutorService {

    @Autowired
    private LivroRepository livroRepository;
    
    @Autowired
    private AutorRepository autorRepository;
    
    @Autowired
    private VinculoLivroAutorRepository vinculoLivroAutorRepository;
    
    @Autowired
    private LivroValidador livroValidador;
    
    @Autowired
    private AutorValidador autorValidador;
    
    public LivroDto vincularLivroAutor(Integer livroId, Integer autorId) {
    	Livro livro = livroValidador.validarLivroExistePeloId(livroId);
        Autor autor = autorValidador.validarAutorExistePeloId(autorId);
        
        vinculoLivroAutorRepository.save(LivroAutor.builder()
        		.livroAutorId(new LivroAutorId(livro.getCodLivro(), autor.getCodAutor()))
        		.build());
        return retornarAutoresLivro(livro.toDTO());
    }
    
    public LivroDto deletarVinculoAutor(Integer livroId, Integer autorId) {
    	Livro livro = livroValidador.validarLivroExistePeloId(livroId);
        Autor autor = autorValidador.validarAutorExistePeloId(autorId);
        
        vinculoLivroAutorRepository.deleteById(new LivroAutorId(livro.getCodLivro(), autor.getCodAutor()));
        return retornarAutoresLivro(livro.toDTO());
    }
    
    public LivroDto retornarAutoresLivro(LivroDto livroDto) {
    	List<Autor> lsAutor = autorRepository.findAllbyIdLivro(livroDto.getCodLivro());
		livroDto.setAutores(lsAutor.stream()
	        .map(Autor::toDTO)
	        .collect(Collectors.toList()));
		return livroDto;
    }
    
    public AutorDto retornarLivrosAutor(AutorDto autorDto) {
    	List<Livro> lsLivro = livroRepository.findAllbyIdAutor(autorDto.getCodAutor());
    	autorDto.setLivros(lsLivro.stream()
	        .map(Livro::toResponseDTO)
	        .collect(Collectors.toList()));
		return autorDto;
    }
}
