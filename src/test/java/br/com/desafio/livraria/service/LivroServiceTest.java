package br.com.desafio.livraria.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import br.com.desafio.livraria.dto.LivroDto;
import br.com.desafio.livraria.dto.Response.LivroResponseDto;
import br.com.desafio.livraria.entity.Livro;
import br.com.desafio.livraria.repository.LivroRepository;
import br.com.desafio.livraria.repository.VinculoLivroAssuntoRepository;
import br.com.desafio.livraria.repository.VinculoLivroAutorRepository;
import br.com.desafio.livraria.validation.LivroValidador;

@ExtendWith(MockitoExtension.class)
class LivroServiceTest {

    @InjectMocks
    private LivroService livroService;

    @Mock
    private LivroRepository livroRepository;

    @Mock
    private VinculoLivroAutorRepository vinculoLivroAutorRepository;
    
    @Mock
    private VinculoLivroAssuntoRepository vinculoLivroAssuntoRepository;

    @Mock
    private VinculoLivroAutorService vinculoLivroAutorService;
    
    @Mock
    private VinculoLivroAssuntoService vinculoLivroAssuntoService;

    @Mock
    private LivroValidador livroValidador;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void salvarLivro_DeveSalvarERetornarDto() {
        LivroDto dto = new LivroDto(null, "Chapeuzinho Vermelho", "Panini", 1, "2013", BigDecimal.ONE, null, null);
        Livro entidade = dto.toEntity();
        Livro entidadeSalva = new Livro(1, "Chapeuzinho Vermelho", "Panini", 1, "2013", 1, BigDecimal.ONE);

        when(livroRepository.save(entidade)).thenReturn(entidadeSalva);
        LivroDto resultado = livroService.salvarLivro(dto);

        assertEquals("Chapeuzinho Vermelho", resultado.getTitulo());
        verify(livroValidador).validarLivroRepetido(dto);
        verify(livroRepository).save(entidade);
    }

    @Test
    void listarPorFiltros_DeveRetornarPaginaDeDtos() {
        String titulo = "Vermelho";
        Pageable pageable = PageRequest.of(0, 10);
        Livro livro = new Livro(1, "Chapeuzinho Vermelho", "Panini", 1, "2013", 1, BigDecimal.ONE);
        Page<Livro> pagina = new PageImpl<>(List.of(livro));

        when(livroRepository.findAll(Mockito.<Specification<Livro>>any(), eq(pageable))).thenReturn(pagina);

        Page<LivroResponseDto> resultado = livroService.listarPorFiltros(titulo, null, null, null, null, null, pageable);

        assertEquals(1, resultado.getTotalElements());
        assertEquals("Chapeuzinho Vermelho", resultado.getContent().get(0).getTitulo());
        assertEquals(pagina.getContent().get(0).getTitulo(), resultado.getContent().get(0).getTitulo());
    }
    
    @Test
    void listarPorFiltros_DeveRetornarPaginaDeDtosFiltroValor() {
        BigDecimal valorMenorQue = BigDecimal.ONE;
        Pageable pageable = PageRequest.of(0, 10);
        Livro livro = new Livro(1, "Chapeuzinho Vermelho", "Panini", 1, "2013", 1, BigDecimal.ONE);
        Page<Livro> pagina = new PageImpl<>(List.of(livro));

        when(livroRepository.findAll(Mockito.<Specification<Livro>>any(), eq(pageable))).thenReturn(pagina);

        Page<LivroResponseDto> resultado = livroService.listarPorFiltros(null, null, null, null, valorMenorQue, null, pageable);

        assertEquals(1, resultado.getTotalElements());
        assertEquals("Chapeuzinho Vermelho", resultado.getContent().get(0).getTitulo());
        assertEquals(pagina.getContent().get(0).getTitulo(), resultado.getContent().get(0).getTitulo());
    }
    
    @Test
    void listarPorFiltros_DeveRetornarPaginaDeDtosSemFiltro() {
    	String titulo = "";
        Pageable pageable = PageRequest.of(0, 10);
        Livro livro = new Livro(1, "Chapeuzinho Vermelho", "Panini", 1, "2013", 1, BigDecimal.ONE);
        Page<Livro> pagina = new PageImpl<>(List.of(livro));

        when(livroRepository.findAll(Mockito.<Specification<Livro>>any(), eq(pageable))).thenReturn(pagina);

        Page<LivroResponseDto> resultado = livroService.listarPorFiltros(titulo, null, null, null, null, null, pageable);

        assertEquals(1, resultado.getTotalElements());
        assertEquals("Chapeuzinho Vermelho", resultado.getContent().get(0).getTitulo());
        assertEquals(pagina.getContent().get(0).getTitulo(), resultado.getContent().get(0).getTitulo());
    }

    @Test
    void buscarPorId_DeveRetornarDtoComAssuntoseAutores() {
    	Livro entidade = new Livro(1, "Chapeuzinho Vermelho", "Panini", 1, "2013", 1, BigDecimal.ONE);
    	LivroDto dto = entidade.toDTO();
    	LivroDto dtoComAssuntoEAutor = new LivroDto(null, "Chapeuzinho Vermelho", "Panini", 1, "2013", BigDecimal.ONE, List.of(), List.of());

        when(livroValidador.validarLivroExistePeloId(1)).thenReturn(entidade);
        when(vinculoLivroAutorService.retornarAutoresLivro(dto)).thenReturn(dtoComAssuntoEAutor);
        when(vinculoLivroAssuntoService.retornarAssuntosLivro(dto)).thenReturn(dtoComAssuntoEAutor);

        LivroDto resultado = livroService.buscarPorId(1);

        assertEquals("Chapeuzinho Vermelho", resultado.getTitulo());
        assertEquals(dtoComAssuntoEAutor.getTitulo(), resultado.getTitulo());
        verify(livroValidador).validarLivroExistePeloId(1);
        verify(vinculoLivroAutorService).retornarAutoresLivro(dto);
        verify(vinculoLivroAssuntoService).retornarAssuntosLivro(dto);
    }

    @Test
    void atualizarLivro_DeveAtualizarERetornarDto() {
    	LivroDto dto = new LivroDto(null, "Titulo Atualizado", "Panini", 1, "2013", BigDecimal.ONE, null, null);
    	Livro entidadeAtualizada = new Livro(2, "Titulo Atualizado", "Panini", 1, "2013", 1, BigDecimal.ONE);

        when(livroRepository.save(any())).thenReturn(entidadeAtualizada);

        LivroDto resultado = livroService.atualizarLivro(2, dto);

        assertEquals(2, resultado.getCodLivro());
        assertEquals("Titulo Atualizado", resultado.getTitulo());
        verify(livroValidador).validarLivroExistePeloId(2);
    }

    @Test
    void deletarAssunto_DeveValidarEDeletar() {
        doNothing().when(vinculoLivroAutorRepository).deletarVinculoByIdLivro(1);
        doNothing().when(vinculoLivroAssuntoRepository).deletarVinculoByIdLivro(1);
        doNothing().when(livroRepository).deleteById(1);

        livroService.deletarLivro(1);

        verify(livroValidador).validarLivroExistePeloId(1);
        verify(vinculoLivroAutorRepository).deletarVinculoByIdLivro(1);
        verify(vinculoLivroAssuntoRepository).deletarVinculoByIdLivro(1);
        verify(livroRepository).deleteById(1);
    }
}
