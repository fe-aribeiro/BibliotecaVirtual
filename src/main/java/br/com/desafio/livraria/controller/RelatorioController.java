
package br.com.desafio.livraria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.desafio.livraria.exception.RelatorioException;
import br.com.desafio.livraria.service.RelatorioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/biblioteca/relatorio")
@Tag(name = "Relatorio", description = "Operações relacionadas ao relatorio.")
public class RelatorioController {
	
	@Autowired
	private RelatorioService livroPorAutorService;
	
	@GetMapping("/pdf")
	@Operation(summary = "Relatorio de livros PDF.", description = "Método responsável por retornar os dados do relatorio em PDF.")
	public ResponseEntity<byte[]> gerarRelatorioAgrupadoPorAutor() {
		
		try {
			byte[] pdf = livroPorAutorService.gerarRelatorioAgrupadoPorAutor();
			
			HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_PDF);
	        headers.setContentDisposition(ContentDisposition.builder("attachment")
	                .filename("relatorio-livros-por-autor.pdf")
	                .build());
	
	        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
		} catch (Exception e) {
            throw new RelatorioException(e.getMessage(), e.getCause());
        }
	}
}
