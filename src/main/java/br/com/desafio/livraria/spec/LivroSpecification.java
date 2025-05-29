package br.com.desafio.livraria.spec;

import org.springframework.data.jpa.domain.Specification;
import br.com.desafio.livraria.entity.Livro;

public class LivroSpecification {

	public static Specification<Livro> tituloContem(String titulo) {
	    return (root, query, cb) -> cb.like(cb.lower(root.get("titulo")), "%" + titulo.toLowerCase() + "%");
	}

	public static Specification<Livro> editoraContem(String editora) {
	    return (root, query, cb) -> cb.like(cb.lower(root.get("editora")), "%" + editora.toLowerCase() + "%");
	}

    public static Specification<Livro> edicaoIgual(Integer edicao) {
        return (root, query, cb) -> cb.equal(root.get("edicao"), edicao);
    }

    public static Specification<Livro> anoPublicacaoIgual(String ano) {
        return (root, query, cb) -> cb.equal(root.get("anoPublicacao"), ano);
    }
    
    public static Specification<Livro> valorMaiorQue(Integer valor) {
        return (root, query, cb) -> cb.greaterThan(root.get("valorCentavos"), valor);
    }

    public static Specification<Livro> valorMenorQue(Integer valor) {
        return (root, query, cb) -> cb.lessThan(root.get("valorCentavos"), valor);
    }
}