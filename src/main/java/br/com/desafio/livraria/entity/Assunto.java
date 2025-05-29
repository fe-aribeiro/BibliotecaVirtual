
package br.com.desafio.livraria.entity;

import br.com.desafio.livraria.dto.AssuntoDto;
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
public class Assunto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_as")
    private Integer codAssunto;
    private String descricao;
    
    public AssuntoDto toDTO() {
        return AssuntoDto.builder()
        		.codAssunto(this.codAssunto)
        		.descricao(this.descricao)
        		.build();
    }
}
