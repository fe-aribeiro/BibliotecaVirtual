
package br.com.desafio.livraria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.desafio.livraria.entity.LivroAutor;
import br.com.desafio.livraria.entity.LivroAutorId;

public interface VinculoLivroAutorRepository extends JpaRepository<LivroAutor, LivroAutorId> {
	
	@Modifying
	@Query(value = "DELETE FROM livro_autor WHERE cod_livro = :codLivro", nativeQuery = true)
	void deletarVinculoByIdLivro(@Param("codLivro") Integer codLivro);
	
	@Modifying
	@Query(value = "DELETE FROM livro_autor WHERE cod_au = :codAu", nativeQuery = true)
	void deletarVinculoByIdAutor(@Param("codAu") Integer codAu);
}
