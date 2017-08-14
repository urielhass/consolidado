package br.com.stock.exception;

public class UserEmailExistingException extends MainException {

	private static final long serialVersionUID = 1L;

	public UserEmailExistingException() {
		this.generateObject("Usuário com e-mail existente.",
				"Gentileza informar um e-mail que não esteja sendo usado por outro usuário.");
	}
}
