package br.com.desafio.livraria.dto.Response;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LivroPorAutorViewDto {

	private Integer codLivro;
	private String nomeAutor;
	private Integer codAutor;
    private String titulo;
    private String editora;
    private Integer edicao;
    private String anoPublicacao;
    private String valor;
    private String assuntos;
    
	public LivroPorAutorViewDto(Integer codLivro, String nomeAutor, Integer codAutor, String titulo, String editora,
			Integer edicao, String anoPublicacao, Integer valor, String assuntos) {
		super();
		this.codLivro = codLivro;
		this.nomeAutor = nomeAutor;
		this.codAutor = codAutor;
		this.titulo = titulo;
		this.editora = editora;
		this.edicao = edicao;
		this.anoPublicacao = anoPublicacao;
		this.valor = formatarPreco(valor);
		this.assuntos = assuntos;
	}
    
	private String formatarPreco(Integer valor) {
        if (valor == null) return null;
        BigDecimal valorBd = valor != null ? new BigDecimal(valor).divide(BigDecimal.valueOf(100)) : null;
        NumberFormat formatador = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return formatador.format(valorBd);
    }
}
