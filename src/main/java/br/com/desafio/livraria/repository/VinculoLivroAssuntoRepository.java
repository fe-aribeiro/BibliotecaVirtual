
package br.com.desafio.livraria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.desafio.livraria.entity.LivroAssunto;
import br.com.desafio.livraria.entity.LivroAssuntoId;

public interface VinculoLivroAssuntoRepository extends JpaRepository<LivroAssunto, LivroAssuntoId> {
	
	@Modifying
	@Query(value = "DELETE FROM livro_assunto WHERE cod_livro = :codLivro", nativeQuery = true)
	void deletarVinculoByIdLivro(@Param("codLivro") Integer codLivro);
	
	@Modifying
	@Query(value = "DELETE FROM livro_assunto WHERE cod_as = :codAs", nativeQuery = true)
	void deletarVinculoByIdAssunto(@Param("codAs") Integer codAs);
}
