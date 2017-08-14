package br.com.stock.exception;

public class RequestExceptionUserAcess extends MainException{

	private static final long serialVersionUID = 1L;
	
	public RequestExceptionUserAcess() {
		this.generateObject("Acesso negado para realizar esta movimentação..",
				"Você não possui acesso para movimentar esta requisição.");
	}
}
