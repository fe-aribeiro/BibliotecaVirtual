
package br.com.desafio.livraria.entity;

import br.com.desafio.livraria.dto.AutorDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_au")
    private Integer codAutor;
    private String nome;

    public AutorDto toDTO() {
        return AutorDto.builder()
        		.codAutor(this.codAutor)
        		.nome(this.nome)
        		.build();
    }
}
