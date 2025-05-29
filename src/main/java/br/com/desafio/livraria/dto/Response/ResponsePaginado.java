package br.com.desafio.livraria.dto.Response;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Data;

@Data
public class ResponsePaginado<T> {

	private List<T> dados;
    private int pagina;
    private int tamanho;
    private long totalElementos;
    private int totalPaginas;
    private boolean ultima;
    private boolean primeira;

    public ResponsePaginado(Page<T> page) {
        this.dados = page.getContent();
        this.pagina = page.getNumber();
        this.tamanho = page.getSize();
        this.totalElementos = page.getTotalElements();
        this.totalPaginas = page.getTotalPages();
        this.ultima = page.isLast();
        this.primeira = page.isFirst();
    }
}
