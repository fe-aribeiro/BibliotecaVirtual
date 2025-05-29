
package br.com.desafio.livraria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.desafio.livraria.entity.Autor;

public interface AutorRepository extends JpaRepository<Autor, Integer>, JpaSpecificationExecutor<Autor> {
	
	@Query(value = "SELECT a.* FROM autor a INNER JOIN livro_autor la ON la.cod_au = a.cod_au WHERE la.cod_livro = :codLivro", nativeQuery = true)
	List<Autor> findAllbyIdLivro(@Param("codLivro") Integer codLivro);
	
	boolean existsByNome(String nome);
}

