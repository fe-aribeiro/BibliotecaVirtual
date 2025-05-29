package br.com.desafio.livraria.dto.Response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.com.desafio.livraria.dto.AssuntoDto;
import br.com.desafio.livraria.dto.AutorDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LivroResponseDto {
    private Integer codLivro;
    private String titulo;
    private String editora;
    private Integer edicao;
    private String anoPublicacao;
    private String valor;
    private List<AutorDto> autores;
    private List<AssuntoDto> assuntos;
}

