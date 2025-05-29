package br.com.desafio.livraria.spec;

import org.springframework.data.jpa.domain.Specification;

import br.com.desafio.livraria.entity.Autor;

public class AutorSpecification {

	public static Specification<Autor> nomeContem(String nome) {
	    return (root, query, cb) -> cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
	}
}