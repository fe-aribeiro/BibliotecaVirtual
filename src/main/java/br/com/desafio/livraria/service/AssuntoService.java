
package br.com.desafio.livraria.service;

import java.util.Objects;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.desafio.livraria.dto.AssuntoDto;
import br.com.desafio.livraria.entity.Assunto;
import br.com.desafio.livraria.repository.AssuntoRepository;
import br.com.desafio.livraria.repository.VinculoLivroAssuntoRepository;
import br.com.desafio.livraria.spec.AssuntoSpecification;
import br.com.desafio.livraria.validation.AssuntoValidador;

@Service
public class AssuntoService {

    @Autowired
    private AssuntoRepository assuntoRepository;
    
    @Autowired
    private VinculoLivroAssuntoRepository vinculoLivroAssuntoRepository;
    
    @Autowired
    private VinculoLivroAssuntoService vinculoLivroAssuntoService;
    
    @Autowired
    private AssuntoValidador assuntoValidador;

    public AssuntoDto salvarAssunto(AssuntoDto assuntoDto) {
    	assuntoValidador.validarAssuntoRepetido(assuntoDto);
        return assuntoRepository.save(assuntoDto.toEntity()).toDTO();
    }

    public Page<AssuntoDto> listarPorFiltros(String descricao, Pageable pageable) {
    	Specification<Assunto> spec = Stream.of(
    			descricao != null && !descricao.isBlank() ? AssuntoSpecification.descricaoContem(descricao) : null
    	    )
    	    .filter(Objects::nonNull)
    	    .reduce((a, b) -> a.and(b))
    	    .orElse((root, query, cb) -> cb.conjunction());
    	
    	Page<Assunto> pagina = assuntoRepository.findAll(spec, pageable);
    	
    	return pagina.map(Assunto::toDTO);
    }

    public AssuntoDto buscarPorId(Integer id) {
    	AssuntoDto assuntoDto = assuntoValidador.validarAssuntoExistePeloId(id).toDTO();
		return vinculoLivroAssuntoService.retornarLivrosAssunto(assuntoDto);
    }

    public AssuntoDto atualizarAssunto(Integer id, AssuntoDto assuntoDto) {
    	assuntoValidador.validarAssuntoExistePeloId(id);
    	assuntoDto.setCodAssunto(id);
        return assuntoRepository.save(assuntoDto.toEntity()).toDTO();
    }

    @Transactional
    public void deletarAssunto(Integer id) {
    	assuntoValidador.validarAssuntoExistePeloId(id);
    	
    	vinculoLivroAssuntoRepository.deletarVinculoByIdAssunto(id);
        assuntoRepository.deleteById(id);
    }
}
