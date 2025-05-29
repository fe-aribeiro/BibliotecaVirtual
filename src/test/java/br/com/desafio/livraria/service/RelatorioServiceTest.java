package br.com.desafio.livraria.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.desafio.livraria.dto.Response.LivroPorAutorViewDto;
import br.com.desafio.livraria.repository.LivroPorAutorViewRepository;
import br.com.desafio.livraria.util.MensagemUtil;

@ExtendWith(MockitoExtension.class)
class RelatorioServiceTest {

    @Mock
    private LivroPorAutorViewRepository repository;

    @Mock
    private MensagemUtil mensagemUtil;

    @InjectMocks
    private RelatorioService relatorioService;

    private LivroPorAutorViewDto livroDto;

    @BeforeEach
    void setup() {
        livroDto = new LivroPorAutorViewDto();
        livroDto.setNomeAutor("Autor Teste");
        livroDto.setTitulo("Livro Teste");
        livroDto.setEditora("Editora Teste");
        livroDto.setEdicao(1);
        livroDto.setAnoPublicacao("2024");
        livroDto.setValor("100.00");
        livroDto.setAssuntos("Tecnologia");
    }

    @Test
    void deveGerarRelatorioComSucesso() throws Exception {
        when(repository.buscarRelatorioPorAutor()).thenReturn(List.of(livroDto));

        byte[] resultado = relatorioService.gerarRelatorioAgrupadoPorAutor();

        assertNotNull(resultado);
        assertTrue(resultado.length > 0);
        verify(repository).buscarRelatorioPorAutor();
    }

    @Test
    void deveLancarRuntimeExceptionQuandoErroAoGerarRelatorio() {
        when(repository.buscarRelatorioPorAutor()).thenThrow(new RuntimeException("Falha simulada"));
        when(mensagemUtil.get("erro.relatorio")).thenReturn("Erro ao gerar relatório");

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            relatorioService.gerarRelatorioAgrupadoPorAutor();
        });

        assertEquals("Erro ao gerar relatório", ex.getMessage());

        // Use times(2) ou remova essa verificação se quiser simplificar
        verify(mensagemUtil, times(2)).get("erro.relatorio");
    }
}
