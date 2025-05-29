package br.com.desafio.livraria.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.com.desafio.livraria.dto.Response.LivroResponseDto;
import br.com.desafio.livraria.entity.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AutorDto {
    private Integer codAutor;
    
    @NotBlank(message = "{erro.validacao.autor.campo.nome.obrigatorio}")
    @Size(max = 40, message = "{erro.validacao.autor.campo.nome.maxCaracteres}")
    private String nome;
    
    private List<LivroResponseDto> livros;
    
    public Autor toEntity() {
        return Autor.builder()
        		.codAutor(this.codAutor)
        		.nome(this.nome)
        		.build();
    }
}

