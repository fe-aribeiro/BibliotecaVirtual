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

import br.com.desafio.livraria.dto.AutorDto;
import br.com.desafio.livraria.dto.LivroDto;
import br.com.desafio.livraria.entity.Autor;
import br.com.desafio.livraria.entity.Livro;
import br.com.desafio.livraria.repository.AutorRepository;
import br.com.desafio.livraria.repository.LivroRepository;
import br.com.desafio.livraria.repository.VinculoLivroAutorRepository;
import br.com.desafio.livraria.validation.AutorValidador;
import br.com.desafio.livraria.validation.LivroValidador;

class VinculoLivroAutorServiceServiceTest {

    @InjectMocks
    private VinculoLivroAutorService vinculoLivroAutorService;
    
    @Mock
    private LivroValidador livroValidador;
    
    @Mock
    private AutorValidador autorValidador;
    
    @Mock
    private VinculoLivroAutorRepository vinculoLivroAutorRepository;
    
    @Mock
    private AutorRepository autorRepository;
    
    @Mock
    private LivroRepository livroRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveVincularLivroAutor() {
    	LivroDto dto = new LivroDto(null, "Chapeuzinho Vermelho", "Panini", 1, "2013", BigDecimal.ONE, null, null);
        Livro entidadeL = dto.toEntity();
        Autor entidadeAu = new Autor(1, "Roberto Araujo");
        List<Autor> lsAutor = List.of(entidadeAu);
        
        when(livroValidador.validarLivroExistePeloId(1)).thenReturn(entidadeL);
        when(autorValidador.validarAutorExistePeloId(1)).thenReturn(entidadeAu);
        when(vinculoLivroAutorRepository.save(any())).thenReturn(null);
        when(autorRepository.findAllbyIdLivro(1)).thenReturn(lsAutor);
        
        LivroDto resultado = vinculoLivroAutorService.vincularLivroAutor(1, 1);

        assertEquals("Chapeuzinho Vermelho", resultado.getTitulo());
        verify(livroValidador).validarLivroExistePeloId(1);
        verify(autorValidador).validarAutorExistePeloId(1);
    }
    
    @Test
    void deveDeletarLivroAssunto() {
    	LivroDto dto = new LivroDto(null, "Chapeuzinho Vermelho", "Panini", 1, "2013", BigDecimal.ONE, null, null);
        Livro entidadeL = dto.toEntity();
        Autor entidadeAu = new Autor(1, "Roberto Araujo");
        List<Autor> lsAutor = List.of(entidadeAu);
        
        when(livroValidador.validarLivroExistePeloId(1)).thenReturn(entidadeL);
        when(autorValidador.validarAutorExistePeloId(1)).thenReturn(entidadeAu);
        doNothing().when(vinculoLivroAutorRepository).deleteById(any());
        when(autorRepository.findAllbyIdLivro(1)).thenReturn(lsAutor);
        
        LivroDto resultado = vinculoLivroAutorService.deletarVinculoAutor(1, 1);

        assertEquals("Chapeuzinho Vermelho", resultado.getTitulo());
        verify(livroValidador).validarLivroExistePeloId(1);
        verify(autorValidador).validarAutorExistePeloId(1);
    }
    
    @Test
    void deveRetornarLivrosAssunto() {
        Livro entidadeL = new Livro(1, "Chapeuzinho Vermelho", "Panini", 1, "2013", 1, BigDecimal.ONE);
        Autor entidadeAu = new Autor(1, "Roberto Araujo");
        AutorDto autorDto = entidadeAu.toDTO();
        List<Livro> lsLivro = List.of(entidadeL);
        
        when(livroRepository.findAllbyIdAssunto(any())).thenReturn(lsLivro);
        
        AutorDto resultado = vinculoLivroAutorService.retornarLivrosAutor(autorDto);

        assertEquals("Roberto Araujo", resultado.getNome());
    }
}
