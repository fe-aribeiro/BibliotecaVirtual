
package br.com.desafio.livraria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.desafio.livraria.entity.Livro;

public interface LivroRepository extends JpaRepository<Livro, Integer>, JpaSpecificationExecutor<Livro> {
	
	@Query(value = "SELECT l.* FROM livro l INNER JOIN livro_autor la ON la.cod_livro = l.cod_livro WHERE la.cod_au = :codAutor", nativeQuery = true)
	List<Livro> findAllbyIdAutor(@Param("codAutor") Integer codAutor);
	
	@Query(value = "SELECT l.* FROM livro l INNER JOIN livro_assunto la ON la.cod_livro = l.cod_livro WHERE la.cod_as = :codAssunto", nativeQuery = true)
	List<Livro> findAllbyIdAssunto(@Param("codAssunto") Integer codAssunto);
	
	boolean existsByTituloAndEditoraAndAnoPublicacaoAndEdicao(String titulo, String editora, String anoPublicacao, Integer edicao);
}
