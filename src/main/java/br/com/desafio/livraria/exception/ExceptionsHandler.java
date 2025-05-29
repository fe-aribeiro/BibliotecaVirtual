package br.com.desafio.livraria.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.desafio.livraria.util.MensagemUtil;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ExceptionsHandler {
	
	@Autowired
	private MensagemUtil mensagemUtil;

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<Object> handleRecursoNaoEncontrado(RecursoNaoEncontradoException ex) {
    	log.error(mensagemUtil.get(ex.getMessage(), ex.getId()));
        return buildErrorResponse(mensagemUtil.get(ex.getMessage(), ex.getId()) , HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(RecursoDuplicadoException.class)
    public ResponseEntity<Object> handleRecursoDuplicadoException(RecursoDuplicadoException ex) {
    	log.error(mensagemUtil.get(ex.getMessage()));
    	return buildErrorResponse(mensagemUtil.get(ex.getMessage()), HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler(RelatorioException.class)
    public ResponseEntity<Object> handleRelatorioException(RelatorioException ex) {
    	log.error(mensagemUtil.get(ex.getMessage()) + ": " + ex.getCause());
    	return buildErrorResponse(mensagemUtil.get(ex.getMessage()) + ": " + ex.getCause(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleCamposInvalidosErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err ->
            errors.put(err.getField(), err.getDefaultMessage())
        );
        log.error(errors.toString());
        return buildErrorResponse(errors, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {
    	log.error(mensagemUtil.get("erro.inesperado", ex.getMessage()));
        return buildErrorResponse(mensagemUtil.get("erro.inesperado", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> buildErrorResponse(Object message, HttpStatus status) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("mensagem", message);

        return new ResponseEntity<>(body, status);
    }
}
