
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
import br.com.desafio.livraria.dto.AutorDto;
import br.com.desafio.livraria.dto.Response.ResponsePaginado;
import br.com.desafio.livraria.service.AutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/biblioteca/autores")
@Tag(name = "Autores", description = "Operações relacionadas a autores.")
public class AutorController {

    @Autowired
    private AutorService autorService;

    @PostMapping
    @Operation(summary = "Adicionar autor.", description = "Método responsável por adicionar autor.")
    public ResponseEntity<AutorDto> adicionarAutor(@Valid @RequestBody AutorDto autorDto) {
        return ResponseEntity.ok(autorService.salvarAutor(autorDto));
    }

    @GetMapping
    @PaginacaoSwaggerOculta
    @Operation(summary = "Listar Autores.", description = "Método responsável por listar autores com filtros e suporte a paginação.")
    public ResponseEntity<ResponsePaginado<AutorDto>> listarAutores(@RequestParam(required = false) String nome,
    		@Parameter(hidden = true) Pageable pageable) {
    	
    	Page<AutorDto> page = autorService.listarPorFiltros(nome, pageable);
    	return ResponseEntity.ok(new ResponsePaginado<>(page));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar autor.", description = "Método responsável por retornar um autor e seus livros pelo id.")
    public ResponseEntity<AutorDto> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(autorService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar autor.", description = "Método responsável por atualizar um autor existente.")
    public ResponseEntity<AutorDto> atualizarAutor(@PathVariable Integer id, @Valid @RequestBody AutorDto autorDto) {
        return ResponseEntity.ok(autorService.atualizarAutor(id, autorDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar autor.", description = "Método responsável por deletar um autor existente e remover os vinculos com livros.")
    public ResponseEntity<Void> deletarAutor(@PathVariable Integer id) {
        autorService.deletarAutor(id);
        return ResponseEntity.noContent().build();
    }
}
