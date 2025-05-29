
package br.com.desafio.livraria.service;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.desafio.livraria.dto.LivroDto;
import br.com.desafio.livraria.dto.Response.LivroResponseDto;
import br.com.desafio.livraria.entity.Livro;
import br.com.desafio.livraria.repository.LivroRepository;
import br.com.desafio.livraria.repository.VinculoLivroAssuntoRepository;
import br.com.desafio.livraria.repository.VinculoLivroAutorRepository;
import br.com.desafio.livraria.spec.LivroSpecification;
import br.com.desafio.livraria.validation.LivroValidador;
import jakarta.transaction.Transactional;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;
    
    @Autowired
    private VinculoLivroAutorService vincularLivroAutorService;
    
    @Autowired
    private VinculoLivroAssuntoService vinculoLivroAssuntoService;
    
    @Autowired
    private VinculoLivroAutorRepository vinculoLivroAutorRepository;
    
    @Autowired
    private VinculoLivroAssuntoRepository vinculoLivroAssuntoRepository;
    
    @Autowired
    private LivroValidador livroValidador;

    public LivroDto salvarLivro(LivroDto livroDto) {
    	livroValidador.validarLivroRepetido(livroDto);
        return livroRepository.save(livroDto.toEntity()).toDTO();
    }
    
    public Page<LivroResponseDto> listarPorFiltros(String titulo, String editora, Integer edicao, String anoPublicacao, BigDecimal valorMenorQue, BigDecimal valorMaiorQue, Pageable pageable) {
    	Specification<Livro> spec = Stream.of(
    	        titulo != null && !titulo.isBlank() ? LivroSpecification.tituloContem(titulo) : null,
    	        editora != null && !editora.isBlank() ? LivroSpecification.editoraContem(editora) : null,
    	        edicao != null ? LivroSpecification.edicaoIgual(edicao) : null,
    	        anoPublicacao != null && !anoPublicacao.isBlank() ? LivroSpecification.anoPublicacaoIgual(anoPublicacao) : null,
        		valorMaiorQue != null ? LivroSpecification.valorMaiorQue(converteParaCentavos(valorMaiorQue)) : null,
				valorMenorQue != null ? LivroSpecification.valorMenorQue(converteParaCentavos(valorMenorQue)) : null
    	    )
    	    .filter(Objects::nonNull)
    	    .reduce((a, b) -> a.and(b))
    	    .orElse((root, query, cb) -> cb.conjunction());
    	
    	Page<Livro> pagina = livroRepository.findAll(spec, pageable);
    	
    	return pagina.map(Livro::toResponseDTO);
    }

    public LivroDto buscarPorId(Integer id) {
    	LivroDto livroDto = livroValidador.validarLivroExistePeloId(id).toDTO();
		vincularLivroAutorService.retornarAutoresLivro(livroDto);
		return vinculoLivroAssuntoService.retornarAssuntosLivro(livroDto);
    }

    public LivroDto atualizarLivro(Integer id, LivroDto livroDto) {
    	livroValidador.validarLivroExistePeloId(id);
    	livroDto.setCodLivro(id);
        return livroRepository.save(livroDto.toEntity()).toDTO();
    }

    @Transactional
    public void deletarLivro(Integer id) {
    	livroValidador.validarLivroExistePeloId(id);
    	
    	vinculoLivroAutorRepository.deletarVinculoByIdLivro(id);
    	vinculoLivroAssuntoRepository.deletarVinculoByIdLivro(id);
        livroRepository.deleteById(id);
    }
    
    private Integer converteParaCentavos(BigDecimal valor) {
    	return valor != null ?
        		valor.multiply(BigDecimal.valueOf(100)).intValueExact() :
                null;
    }
}
