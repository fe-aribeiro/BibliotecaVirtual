
package br.com.desafio.livraria.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.desafio.livraria.anotation.PaginacaoSwaggerOculta;
import br.com.desafio.livraria.dto.LivroDto;
import br.com.desafio.livraria.dto.Response.LivroResponseDto;
import br.com.desafio.livraria.dto.Response.ResponsePaginado;
import br.com.desafio.livraria.service.LivroService;
import br.com.desafio.livraria.service.VinculoLivroAssuntoService;
import br.com.desafio.livraria.service.VinculoLivroAutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/biblioteca/livros")
@Tag(name = "Livros", description = "Operações relacionadas a livros.")
public class LivroController {
	
    @Autowired
    private LivroService livroService;
    
    @Autowired
    private VinculoLivroAutorService vincularLivroAutorService;
    
    @Autowired
    private VinculoLivroAssuntoService vincularLivroAssuntoService;

    @PostMapping
    @Operation(summary = "Adicionar livro.", description = "Método responsável por adicionar livro.")
    public ResponseEntity<LivroResponseDto> adicionarLivro(@Valid @RequestBody LivroDto livroDto) {
        return ResponseEntity.ok(livroService.salvarLivro(livroDto).toResponseDTO());
    }
    
    @PostMapping("/{livroId}/autores/{autorId}")
    @Operation(summary = "Vincular autor.", description = "Método responsável por vincular um autor a um livro pelo id.")
    public ResponseEntity<LivroResponseDto> vincularAutor(@PathVariable Integer livroId, @PathVariable Integer autorId) {
    	return ResponseEntity.ok(vincularLivroAutorService.vincularLivroAutor(livroId, autorId).toResponseDTO());
    }
    
    @DeleteMapping("/{livroId}/autores/{autorId}")
    @Operation(summary = "Remover vinculo autor.", description = "Método responsável por remover o vinculo de um autor com um livro pelo id.")
    public ResponseEntity<LivroResponseDto> removerVinculoAutor(@PathVariable Integer livroId, @PathVariable Integer autorId) {
    	return ResponseEntity.ok(vincularLivroAutorService.deletarVinculoAutor(livroId, autorId).toResponseDTO());
    }
    
    @PostMapping("/{livroId}/assuntos/{assuntoId}")
    @Operation(summary = "Vincular assunto.", description = "Método responsável por vincular um assunto a um livro pelo id.")
    public ResponseEntity<LivroResponseDto> vincularAssunto(@PathVariable Integer livroId, @PathVariable Integer assuntoId) {
    	return ResponseEntity.ok(vincularLivroAssuntoService.vincularLivroAssunto(livroId, assuntoId).toResponseDTO());
    }
    
    @DeleteMapping("/{livroId}/assuntos/{assuntoId}")
    @Operation(summary = "Remover vinculo assunto.", description = "Método responsável por remover o vinculo de um assunto com um livro pelo id.")
    public ResponseEntity<LivroResponseDto> removerVinculoAssunto(@PathVariable Integer livroId, @PathVariable Integer assuntoId) {
    	return ResponseEntity.ok(vincularLivroAssuntoService.deletarVinculoAssunto(livroId, assuntoId).toResponseDTO());
    }

    @GetMapping
    @PaginacaoSwaggerOculta
    @Operation(summary = "Listar livros.", description = "Método responsável por listar livros com filtros e suporte a paginação.")
    public ResponseEntity<ResponsePaginado<LivroResponseDto>> listarLivros(@RequestParam(required = false) String titulo,
    	    @RequestParam(required = false) String editora,
    	    @RequestParam(required = false) Integer edicao,
    	    @RequestParam(required = false) String anoPublicacao,
    	    @RequestParam(required = false) BigDecimal valorMenorQue,
    	    @RequestParam(required = false) BigDecimal valorMaiorQue,
    	    @Parameter(hidden = true) Pageable pageable) {
    	
    	Page<LivroResponseDto> page = livroService.listarPorFiltros(titulo, editora, edicao, anoPublicacao, valorMenorQue, valorMaiorQue, pageable);
	    return ResponseEntity.ok(new ResponsePaginado<>(page));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar livro.", description = "Método responsável por retornar um livro e seus vinculos pelo id.")
    public ResponseEntity<LivroResponseDto> buscarPorId(@PathVariable Integer id) {
    	return ResponseEntity.ok(livroService.buscarPorId(id).toResponseDTO());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar livro.", description = "Método responsável por atualizar um livro existente.")
    public ResponseEntity<LivroResponseDto> atualizarLivro(@PathVariable Integer id, @Valid @RequestBody LivroDto livroDto) {
        return ResponseEntity.ok(livroService.atualizarLivro(id, livroDto).toResponseDTO());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar livro.", description = "Método responsável por deletar um livro existente e remover os vinculos com autores e assuntos.")
    public ResponseEntity<Void> deletarLivro(@PathVariable Integer id) {
        livroService.deletarLivro(id);
        return ResponseEntity.noContent().build();
    }
}
