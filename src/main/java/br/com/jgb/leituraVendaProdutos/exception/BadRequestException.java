package br.com.jgb.leituraVendaProdutos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção customizada parta mostrar os erros de validação
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = 2693108697040844778L;

	public BadRequestException(String message) {
		super(message);
	}
}