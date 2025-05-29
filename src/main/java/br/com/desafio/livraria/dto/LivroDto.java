package br.com.desafio.livraria.dto;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.com.desafio.livraria.anotation.AnoPublicacaoValido;
import br.com.desafio.livraria.dto.Response.LivroResponseDto;
import br.com.desafio.livraria.entity.Livro;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LivroDto {
    private Integer codLivro;
    
    @NotBlank(message = "{erro.validacao.livro.campo.titulo.obrigatorio}")
    @Size(max = 40, message = "{erro.validacao.livro.campo.titulo.maxCaracteres}")
    private String titulo;
    
    @NotBlank(message = "{erro.validacao.livro.campo.editora.obrigatorio}")
    @Size(max = 40, message = "{erro.validacao.livro.campo.editora.maxCaracteres}")
    private String editora;
    
    @NotNull(message = "{erro.validacao.livro.campo.edicao.obrigatorio}")
    @Min(value = 1, message = "{erro.validacao.livro.campo.edicao.maiorQueZero}")
    private Integer edicao;
    
    @NotNull(message = "{erro.validacao.livro.campo.anoPublicacao.obrigatorio}")
    @AnoPublicacaoValido(message = "{erro.validacao.livro.campo.anoPublicacao.anoInvalido}")
    private String anoPublicacao;
    
    @NotNull(message = "{erro.validacao.livro.campo.valor.obrigatorio}")
    @DecimalMin(value = "0.01", inclusive = true, message = "{erro.validacao.livro.campo.valor.maiorQueZero}")
    private BigDecimal valor;
    
    private List<AutorDto> autores;
    private List<AssuntoDto> assuntos;
    
    public Livro toEntity() {
        return Livro.builder()
        		.codLivro(this.getCodLivro())
        		.titulo(this.getTitulo())
        		.editora(this.getEditora())
        		.edicao(this.getEdicao())
        		.anoPublicacao(this.getAnoPublicacao())
        		.valorReais(this.getValor())
        		.valorCentavos(valor != null ? valor.multiply(BigDecimal.valueOf(100)).intValueExact() : null)
        		.build();
    }
    
    public LivroResponseDto toResponseDTO() {
    	return LivroResponseDto.builder()
        		.codLivro(this.codLivro)
        		.titulo(this.titulo)
        		.editora(this.editora)
        		.edicao(this.edicao)
        		.anoPublicacao(this.anoPublicacao)
        		.valor(formatarPreco(this.valor))
        		.autores(this.autores)
        		.assuntos(this.assuntos)
        		.build();
    }
    
    private String formatarPreco(BigDecimal valor) {
        if (valor == null) return null;
        NumberFormat formatador = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return formatador.format(valor);
    }
}

