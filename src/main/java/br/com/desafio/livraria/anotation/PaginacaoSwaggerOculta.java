package br.com.desafio.livraria.anotation;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Parameters({
    @Parameter(name = "pagina", description = "Número da página", in = ParameterIn.QUERY,
               schema = @Schema(type = "integer", defaultValue = "0")),
    @Parameter(name = "tamanho", description = "Tamanho da página", in = ParameterIn.QUERY,
               schema = @Schema(type = "integer", defaultValue = "5")),
    @Parameter(name = "ordenarPor", description = "Campo para ordenação", in = ParameterIn.QUERY,
               schema = @Schema(type = "string"))
})
public @interface PaginacaoSwaggerOculta {
}
