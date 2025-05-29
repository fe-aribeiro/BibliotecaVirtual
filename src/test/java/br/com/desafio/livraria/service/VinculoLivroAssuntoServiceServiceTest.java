package br.com.desafio.livraria.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.desafio.livraria.dto.AssuntoDto;
import br.com.desafio.livraria.dto.LivroDto;
import br.com.desafio.livraria.entity.Assunto;
import br.com.desafio.livraria.entity.Livro;
import br.com.desafio.livraria.repository.AssuntoRepository;
import br.com.desafio.livraria.repository.LivroRepository;
import br.com.desafio.livraria.repository.VinculoLivroAssuntoRepository;
import br.com.desafio.livraria.validation.AssuntoValidador;
import br.com.desafio.livraria.validation.LivroValidador;

class VinculoLivroAssuntoServiceServiceTest {

    @InjectMocks
    private VinculoLivroAssuntoService vinculoLivroAssuntoService;
    
    @Mock
    private LivroValidador livroValidador;
    
    @Mock
    private AssuntoValidador assuntoValidador;
    
    @Mock
    private VinculoLivroAssuntoRepository vinculoLivroAssuntoRepository;
    
    @Mock
    private AssuntoRepository assuntoRepository;
    
    @Mock
    private LivroRepository livroRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveVincularLivroAssunto() {
    	LivroDto dto = new LivroDto(null, "Chapeuzinho Vermelho", "Panini", 1, "2013", BigDecimal.ONE, null, null);
        Livro entidadeL = dto.toEntity();
        Assunto entidadeAs = new Assunto(1, "Filosofia");
        List<Assunto> lsAssunto = List.of(entidadeAs);
        
        when(livroValidador.validarLivroExistePeloId(1)).thenReturn(entidadeL);
        when(assuntoValidador.validarAssuntoExistePeloId(1)).thenReturn(entidadeAs);
        when(vinculoLivroAssuntoRepository.save(any())).thenReturn(null);
        when(assuntoRepository.findAllbyIdLivro(1)).thenReturn(lsAssunto);
        
        LivroDto resultado = vinculoLivroAssuntoService.vincularLivroAssunto(1, 1);

        assertEquals("Chapeuzinho Vermelho", resultado.getTitulo());
        verify(livroValidador).validarLivroExistePeloId(1);
        verify(assuntoValidador).validarAssuntoExistePeloId(1);
    }
    
    @Test
    void deveDeletarLivroAssunto() {
    	LivroDto dto = new LivroDto(null, "Chapeuzinho Vermelho", "Panini", 1, "2013", BigDecimal.ONE, null, null);
        Livro entidadeL = dto.toEntity();
        Assunto entidadeAs = new Assunto(1, "Filosofia");
        List<Assunto> lsAssunto = List.of(entidadeAs);
        
        when(livroValidador.validarLivroExistePeloId(1)).thenReturn(entidadeL);
        when(assuntoValidador.validarAssuntoExistePeloId(1)).thenReturn(entidadeAs);
        doNothing().when(vinculoLivroAssuntoRepository).deleteById(any());
        when(assuntoRepository.findAllbyIdLivro(1)).thenReturn(lsAssunto);
        
        LivroDto resultado = vinculoLivroAssuntoService.deletarVinculoAssunto(1, 1);

        assertEquals("Chapeuzinho Vermelho", resultado.getTitulo());
        verify(livroValidador).validarLivroExistePeloId(1);
        verify(assuntoValidador).validarAssuntoExistePeloId(1);
    }
    
    @Test
    void deveRetornarLivrosAssunto() {
        Livro entidadeL = new Livro(1, "Chapeuzinho Vermelho", "Panini", 1, "2013", 1, BigDecimal.ONE);
        Assunto entidadeAs = new Assunto(1, "Filosofia");
        AssuntoDto assuntoDto = entidadeAs.toDTO();
        List<Livro> lsLivro = List.of(entidadeL);
        
        when(livroRepository.findAllbyIdAssunto(any())).thenReturn(lsLivro);
        
        AssuntoDto resultado = vinculoLivroAssuntoService.retornarLivrosAssunto(assuntoDto);

        assertEquals("Filosofia", resultado.getDescricao());
    }
}
