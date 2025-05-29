package br.com.desafio.livraria.entity.view;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Immutable
@Table(name = "vw_livros_por_autor")
@Data
public class LivroPorAutorView {

    @Id
    @Column(name = "cod_livro")
    private Integer codLivro;
    
    @Column(name = "nome_autor")
    private String nomeAutor;
    
    @Column(name = "cod_autor")
    private Integer codAutor;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "editora")
    private String editora;

    @Column(name = "edicao")
    private Integer edicao;

    @Column(name = "ano_publicacao")
    private String anoPublicacao;

    @Column(name = "valor")
    private Integer valor;

    @Column(name = "assuntos")
    private String assuntos;
}
