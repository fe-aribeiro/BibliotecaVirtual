package br.com.desafio.livraria.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.desafio.livraria.dto.Response.LivroPorAutorViewDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class LivroPorAutorViewRepository {
	
	@PersistenceContext
    private EntityManager entityManager;

    public List<LivroPorAutorViewDto> buscarRelatorioPorAutor() {
        return entityManager.createQuery(
                "SELECT new br.com.desafio.livraria.dto.Response.LivroPorAutorViewDto(" +
                "v.codLivro, v.nomeAutor, v.codAutor, v.titulo, v.editora, v.edicao, v.anoPublicacao, v.valor, v.assuntos) " +
                "FROM LivroPorAutorView v", LivroPorAutorViewDto.class)
            .getResultList();
    }
}
