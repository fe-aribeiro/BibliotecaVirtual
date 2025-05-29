package br.com.desafio.livraria.util;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MensagemUtil {

    private final MessageSource messageSource;

    public MensagemUtil(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String get(String chave, Object... parametros) {
        return messageSource.getMessage(chave, parametros, Locale.getDefault());
    }
}