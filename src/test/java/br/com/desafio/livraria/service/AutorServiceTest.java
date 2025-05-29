package br.com.desafio.livraria.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import br.com.desafio.livraria.dto.AutorDto;
import br.com.desafio.livraria.entity.Autor;
import br.com.desafio.livraria.repository.AutorRepository;
import br.com.desafio.livraria.repository.VinculoLivroAutorRepository;
import br.com.desafio.livraria.validation.AutorValidador;

@ExtendWith(MockitoExtension.class)
class AutorServiceTest {

    @InjectMocks
    private AutorService autorService;

    @Mock
    private AutorRepository autorRepository;

    @Mock
    private VinculoLivroAutorRepository vinculoLivroAutorRepository;

    @Mock
    private VinculoLivroAutorService vinculoLivroAutorService;

    @Mock
    private AutorValidador autorValidador;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void salvarAutor_DeveSalvarERetornarDto() {
        AutorDto dto = new AutorDto(null, "Clarice Lispector", null);
        Autor entidade = dto.toEntity();
        Autor entidadeSalva = new Autor(1, "Clarice Lispector");

        when(autorRepository.save(entidade)).thenReturn(entidadeSalva);
        AutorDto resultado = autorService.salvarAutor(dto);

        assertEquals("Clarice Lispector", resultado.getNome());
        verify(autorValidador).validarAutorRepetido(dto);
        verify(autorRepository).save(entidade);
    }

    @Test
    void listarPorFiltros_DeveRetornarPaginaDeDtos() {
        String nome = "Lispector";
        Pageable pageable = PageRequest.of(0, 10);
        Autor autor = new Autor(1, "Clarice Lispector");
        Page<Autor> pagina = new PageImpl<>(List.of(autor));

        when(autorRepository.findAll(Mockito.<Specification<Autor>>any(), eq(pageable))).thenReturn(pagina);

        Page<AutorDto> resultado = autorService.listarPorFiltros(nome, pageable);

        assertEquals(1, resultado.getTotalElements());
        assertEquals("Clarice Lispector", resultado.getContent().get(0).getNome());
    }
    
    @Test
    void listarPorFiltros_DeveRetornarPaginaDeDtosSemFiltro() {
    	String nome = "";
        Pageable pageable = PageRequest.of(0, 10);
        Autor autor = new Autor(1, "Clarice Lispector");
        Page<Autor> pagina = new PageImpl<>(List.of(autor));

        when(autorRepository.findAll(Mockito.<Specification<Autor>>any(), eq(pageable))).thenReturn(pagina);

        Page<AutorDto> resultado = autorService.listarPorFiltros(nome, pageable);

        assertEquals(1, resultado.getTotalElements());
        assertEquals("Clarice Lispector", resultado.getContent().get(0).getNome());
    }

    @Test
    void buscarPorId_DeveRetornarDtoComLivros() {
    	Autor entidade = new Autor(1, "Clarice Lispector");
    	AutorDto dto = entidade.toDTO();
    	AutorDto dtoComLivros = new AutorDto(1, "Clarice Lispector", List.of());

        when(autorValidador.validarAutorExistePeloId(1)).thenReturn(entidade);
        when(vinculoLivroAutorService.retornarLivrosAutor(dto)).thenReturn(dtoComLivros);

        AutorDto resultado = autorService.buscarPorId(1);

        assertEquals("Clarice Lispector", resultado.getNome());
        verify(autorValidador).validarAutorExistePeloId(1);
        verify(vinculoLivroAutorService).retornarLivrosAutor(dto);
    }

    @Test
    void atualizarAutor_DeveAtualizarERetornarDto() {
    	AutorDto dto = new AutorDto(null, "Nome Atualizado", null);
        Autor entidadeAtualizada = new Autor(2, "Nome Atualizado");

        when(autorRepository.save(any())).thenReturn(entidadeAtualizada);

        AutorDto resultado = autorService.atualizarAutor(2, dto);

        assertEquals(2, resultado.getCodAutor());
        assertEquals("Nome Atualizado", resultado.getNome());
        verify(autorValidador).validarAutorExistePeloId(2);
    }

    @Test
    void deletarAssunto_DeveValidarEDeletar() {
        doNothing().when(vinculoLivroAutorRepository).deletarVinculoByIdAutor(1);
        doNothing().when(autorRepository).deleteById(1);

        autorService.deletarAutor(1);

        verify(autorValidador).validarAutorExistePeloId(1);
        verify(vinculoLivroAutorRepository).deletarVinculoByIdAutor(1);
        verify(autorRepository).deleteById(1);
    }
}
