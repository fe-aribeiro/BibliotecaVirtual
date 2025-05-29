
package br.com.desafio.livraria.service;

import java.util.Objects;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.desafio.livraria.dto.AutorDto;
import br.com.desafio.livraria.entity.Autor;
import br.com.desafio.livraria.repository.AutorRepository;
import br.com.desafio.livraria.repository.VinculoLivroAutorRepository;
import br.com.desafio.livraria.spec.AutorSpecification;
import br.com.desafio.livraria.validation.AutorValidador;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;
    
    @Autowired
    private VinculoLivroAutorRepository vinculoLivroAutorRepository;
    
    @Autowired
    private VinculoLivroAutorService vinculoLivroAutorService;
    
    @Autowired
    private AutorValidador autorValidador;

    public AutorDto salvarAutor(AutorDto autorDto) {
    	autorValidador.validarAutorRepetido(autorDto);
        return autorRepository.save(autorDto.toEntity()).toDTO();
    }

    public Page<AutorDto> listarPorFiltros(String nome, Pageable pageable) {
    	Specification<Autor> spec = Stream.of(
    			nome != null && !nome.isBlank() ? AutorSpecification.nomeContem(nome) : null
    	    )
    	    .filter(Objects::nonNull)
    	    .reduce((a, b) -> a.and(b))
    	    .orElse((root, query, cb) -> cb.conjunction());
    	
    	Page<Autor> pagina = autorRepository.findAll(spec, pageable);
    	
    	return pagina.map(Autor::toDTO);
    }

    public AutorDto buscarPorId(Integer id) {
    	AutorDto autorDto = autorValidador.validarAutorExistePeloId(id).toDTO();
		return vinculoLivroAutorService.retornarLivrosAutor(autorDto);
    }

    public AutorDto atualizarAutor(Integer id, AutorDto autorDto) {
    	autorValidador.validarAutorExistePeloId(id);
    	autorDto.setCodAutor(id);
        return autorRepository.save(autorDto.toEntity()).toDTO();
    }

    @Transactional
    public void deletarAutor(Integer id) {
    	autorValidador.validarAutorExistePeloId(id);
    	
    	vinculoLivroAutorRepository.deletarVinculoByIdAutor(id);
        autorRepository.deleteById(id);
    }
}
