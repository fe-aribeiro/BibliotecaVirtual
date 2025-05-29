
package br.com.desafio.livraria.entity;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import br.com.desafio.livraria.dto.LivroDto;
import br.com.desafio.livraria.dto.Response.LivroResponseDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codLivro;

    private String titulo;
    private String editora;
    private Integer edicao;
    private String anoPublicacao;
    @Column(name = "valor")
    private Integer valorCentavos;
    
    @Transient
    private BigDecimal valorReais;
    
    public BigDecimal getValor() {
        return valorCentavos != null ?
                new BigDecimal(valorCentavos).divide(BigDecimal.valueOf(100)) :
                null;
    }

    public void setValor(BigDecimal valor) {
        this.valorReais = valor;
        this.valorCentavos = valor != null ?
        		valor.multiply(BigDecimal.valueOf(100)).intValueExact() :
                null;
    }
    
    public LivroDto toDTO() {
    	return LivroDto.builder()
        		.codLivro(this.codLivro)
        		.titulo(this.titulo)
        		.editora(this.editora)
        		.edicao(this.edicao)
        		.anoPublicacao(this.anoPublicacao)
        		.valor(valorCentavos != null ? new BigDecimal(valorCentavos).divide(BigDecimal.valueOf(100)) : null)
        		.build();
    }
    
    public LivroResponseDto toResponseDTO() {
    	return LivroResponseDto.builder()
        		.codLivro(this.codLivro)
        		.titulo(this.titulo)
        		.editora(this.editora)
        		.edicao(this.edicao)
        		.anoPublicacao(this.anoPublicacao)
        		.valor(formatarPreco(this.valorCentavos))
        		.build();
    }
    
    private String formatarPreco(Integer valorCentavos) {
        if (valorCentavos == null) return null;
        BigDecimal valor = BigDecimal.valueOf(valorCentavos).divide(BigDecimal.valueOf(100));
        NumberFormat formatador = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return formatador.format(valor);
    }
}
