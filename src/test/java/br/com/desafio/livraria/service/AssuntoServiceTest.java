package br.com.desafio.livraria.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.eq;

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

import br.com.desafio.livraria.dto.AssuntoDto;
import br.com.desafio.livraria.entity.Assunto;
import br.com.desafio.livraria.repository.AssuntoRepository;
import br.com.desafio.livraria.repository.VinculoLivroAssuntoRepository;
import br.com.desafio.livraria.validation.AssuntoValidador;

@ExtendWith(MockitoExtension.class)
class AssuntoServiceTest {

    @InjectMocks
    private AssuntoService assuntoService;

    @Mock
    private AssuntoRepository assuntoRepository;

    @Mock
    private VinculoLivroAssuntoRepository vinculoLivroAssuntoRepository;

    @Mock
    private VinculoLivroAssuntoService vinculoLivroAssuntoService;

    @Mock
    private AssuntoValidador assuntoValidador;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void salvarAssunto_DeveSalvarERetornarDto() {
        AssuntoDto dto = new AssuntoDto(null, "Tecnologia", null);
        Assunto entidade = dto.toEntity();
        Assunto entidadeSalva = new Assunto(1, "Tecnologia");

        when(assuntoRepository.save(entidade)).thenReturn(entidadeSalva);
        AssuntoDto resultado = assuntoService.salvarAssunto(dto);

        assertEquals("Tecnologia", resultado.getDescricao());
        verify(assuntoValidador).validarAssuntoRepetido(dto);
        verify(assuntoRepository).save(entidade);
    }

    @Test
    void listarPorFiltros_DeveRetornarPaginaDeDtos() {
        String descricao = "História";
        Pageable pageable = PageRequest.of(0, 10);
        Assunto assunto = new Assunto(1, "História Antiga");
        Page<Assunto> pagina = new PageImpl<>(List.of(assunto));

        when(assuntoRepository.findAll(Mockito.<Specification<Assunto>>any(), eq(pageable))).thenReturn(pagina);

        Page<AssuntoDto> resultado = assuntoService.listarPorFiltros(descricao, pageable);

        assertEquals(1, resultado.getTotalElements());
        assertEquals("História Antiga", resultado.getContent().get(0).getDescricao());
    }
    
    @Test
    void listarPorFiltros_DeveRetornarPaginaDeDtosSemFiltro() {
        String descricao = "";
        Pageable pageable = PageRequest.of(0, 10);
        Assunto assunto = new Assunto(1, "História Antiga");
        Page<Assunto> pagina = new PageImpl<>(List.of(assunto));

        when(assuntoRepository.findAll(Mockito.<Specification<Assunto>>any(), eq(pageable))).thenReturn(pagina);

        Page<AssuntoDto> resultado = assuntoService.listarPorFiltros(descricao, pageable);

        assertEquals(1, resultado.getTotalElements());
        assertEquals("História Antiga", resultado.getContent().get(0).getDescricao());
    }

    @Test
    void buscarPorId_DeveRetornarDtoComLivros() {
        Assunto entidade = new Assunto(1, "Filosofia");
        AssuntoDto dto = entidade.toDTO();
        AssuntoDto dtoComLivros = new AssuntoDto(1, "Filosofia", List.of());

        when(assuntoValidador.validarAssuntoExistePeloId(1)).thenReturn(entidade);
        when(vinculoLivroAssuntoService.retornarLivrosAssunto(dto)).thenReturn(dtoComLivros);

        AssuntoDto resultado = assuntoService.buscarPorId(1);

        assertEquals("Filosofia", resultado.getDescricao());
        verify(assuntoValidador).validarAssuntoExistePeloId(1);
        verify(vinculoLivroAssuntoService).retornarLivrosAssunto(dto);
    }

    @Test
    void atualizarAssunto_DeveAtualizarERetornarDto() {
        AssuntoDto dto = new AssuntoDto(null, "Atualizado", null);
        Assunto entidadeAtualizada = new Assunto(2, "Atualizado");

        when(assuntoRepository.save(any())).thenReturn(entidadeAtualizada);

        AssuntoDto resultado = assuntoService.atualizarAssunto(2, dto);

        assertEquals(2, resultado.getCodAssunto());
        assertEquals("Atualizado", resultado.getDescricao());
        verify(assuntoValidador).validarAssuntoExistePeloId(2);
    }

    @Test
    void deletarAssunto_DeveValidarEDeletar() {
        doNothing().when(vinculoLivroAssuntoRepository).deletarVinculoByIdAssunto(1);
        doNothing().when(assuntoRepository).deleteById(1);

        assuntoService.deletarAssunto(1);

        verify(assuntoValidador).validarAssuntoExistePeloId(1);
        verify(vinculoLivroAssuntoRepository).deletarVinculoByIdAssunto(1);
        verify(assuntoRepository).deleteById(1);
    }
}
