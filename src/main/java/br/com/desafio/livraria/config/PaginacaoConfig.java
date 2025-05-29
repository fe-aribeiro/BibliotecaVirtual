package br.com.desafio.livraria.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class PaginacaoConfig implements WebMvcConfigurer {

	@Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        SortHandlerMethodArgumentResolver sortResolver = new SortHandlerMethodArgumentResolver();
        sortResolver.setSortParameter("ordenarPor");

        PageableHandlerMethodArgumentResolver pageableResolver = new PageableHandlerMethodArgumentResolver(sortResolver);
        pageableResolver.setPageParameterName("pagina");
        pageableResolver.setSizeParameterName("tamanho");

        resolvers.add(pageableResolver);
	}
}