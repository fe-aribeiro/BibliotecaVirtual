
package br.com.desafio.livraria.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor 
public class LivroAutorId { 
	private Integer codLivro;
	@Column(name = "cod_au")
    private Integer codAutor;
}