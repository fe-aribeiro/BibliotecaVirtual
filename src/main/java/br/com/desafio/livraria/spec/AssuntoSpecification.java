package br.com.desafio.livraria.spec;

import org.springframework.data.jpa.domain.Specification;

import br.com.desafio.livraria.entity.Assunto;

public class AssuntoSpecification {

	public static Specification<Assunto> descricaoContem(String descricao) {
	    return (root, query, cb) -> cb.like(cb.lower(root.get("descricao")), "%" + descricao.toLowerCase() + "%");
	}
}