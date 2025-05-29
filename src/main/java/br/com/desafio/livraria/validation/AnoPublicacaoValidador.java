package br.com.desafio.livraria.validation;

import java.time.Year;

import br.com.desafio.livraria.anotation.AnoPublicacaoValido;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AnoPublicacaoValidador implements ConstraintValidator<AnoPublicacaoValido, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }

        try {
            int ano = Integer.parseInt(value);
            int anoAtual = Year.now().getValue();
            return ano <= anoAtual;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
