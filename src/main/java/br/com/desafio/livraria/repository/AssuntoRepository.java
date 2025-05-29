
package br.com.desafio.livraria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.desafio.livraria.entity.Assunto;

public interface AssuntoRepository extends JpaRepository<Assunto, Integer>, JpaSpecificationExecutor<Assunto> {
	
	@Query(value = "SELECT a.* FROM assunto a INNER JOIN livro_assunto la ON la.cod_as = a.cod_as WHERE la.cod_livro = :codLivro", nativeQuery = true)
	List<Assunto> findAllbyIdLivro(@Param("codLivro") Integer codLivro);
	
	boolean existsByDescricao(String descricao);
}
