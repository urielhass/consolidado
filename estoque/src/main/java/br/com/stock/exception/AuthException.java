package br.com.stock.exception;

public class AuthException extends MainException {

	private static final long serialVersionUID = 1L;

	public AuthException() {
		this.generateObject("Email ou senha inválidos.",
				"Não foram encontrados registro no banco com seu email e senha.");
	}
}
