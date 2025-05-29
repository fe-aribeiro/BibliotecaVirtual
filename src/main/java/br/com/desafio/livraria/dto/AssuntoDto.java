package br.com.desafio.livraria.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.com.desafio.livraria.dto.Response.LivroResponseDto;
import br.com.desafio.livraria.entity.Assunto;
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
public class AssuntoDto {
    private Integer codAssunto;
    
    @NotBlank(message = "{erro.validacao.assunto.campo.descricao.obrigatorio}")
    @Size(max = 20, message = "{erro.validacao.assunto.campo.descricao.maxCaracteres}")
    private String descricao;
    
    private List<LivroResponseDto> livros;
    
    public Assunto toEntity() {
        return Assunto.builder()
        		.codAssunto(this.codAssunto)
        		.descricao(this.descricao)
        		.build();
    }
}

