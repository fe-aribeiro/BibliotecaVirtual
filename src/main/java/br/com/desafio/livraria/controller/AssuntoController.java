
package br.com.desafio.livraria.controller;

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
import br.com.desafio.livraria.dto.AssuntoDto;
import br.com.desafio.livraria.dto.Response.ResponsePaginado;
import br.com.desafio.livraria.service.AssuntoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/biblioteca/assuntos")
@Tag(name = "Assuntos", description = "Operações relacionadas a assuntos.")
public class AssuntoController {

    @Autowired
    private AssuntoService assuntoService;

    @PostMapping
    @Operation(summary = "Adicionar assunto.", description = "Método responsável por adicionar assunto.")
    public ResponseEntity<AssuntoDto> adicionarAssunto(@Valid @RequestBody AssuntoDto assuntoDto) {
        return ResponseEntity.ok(assuntoService.salvarAssunto(assuntoDto));
    }

    @GetMapping
    @PaginacaoSwaggerOculta
    @Operation(summary = "Listar Assuntos.", description = "Método responsável por listar assuntos com filtros e suporte a paginação.")
    public ResponseEntity<ResponsePaginado<AssuntoDto>> listarAssuntos(@RequestParam(required = false) String descricao,
    		@Parameter(hidden = true) Pageable pageable) {
    	
    	Page<AssuntoDto> page = assuntoService.listarPorFiltros(descricao, pageable);
    	return ResponseEntity.ok(new ResponsePaginado<>(page));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar assunto.", description = "Método responsável por retornar um assunto e seus livros pelo id.")
    public ResponseEntity<AssuntoDto> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(assuntoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar assunto.", description = "Método responsável por atualizar um assunto existente.")
    public ResponseEntity<AssuntoDto> atualizarAssunto(@PathVariable Integer id, @Valid @RequestBody AssuntoDto assuntoDto) {
        return ResponseEntity.ok(assuntoService.atualizarAssunto(id, assuntoDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar assunto.", description = "Método responsável por deletar um assunto existente e remover os vinculos com livros.")
    public ResponseEntity<Void> deletarAssunto(@PathVariable Integer id) {
        assuntoService.deletarAssunto(id);
        return ResponseEntity.noContent().build();
    }
}
