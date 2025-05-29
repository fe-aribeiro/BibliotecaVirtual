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
    
    @NotBlank(message = "O campo nome não pode estar vazio")
    @Size(max = 40, message = "O campo nome não pode conter mais de 40 caracteres")
    private String nome;
    
    private List<LivroResponseDto> livros;
    
    public Autor toEntity() {
        return Autor.builder()
        		.codAutor(this.codAutor)
        		.nome(this.nome)
        		.build();
    }
}

