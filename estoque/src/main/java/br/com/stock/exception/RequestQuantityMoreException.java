package br.com.stock.exception;

public class RequestQuantityMoreException extends MainException{

	private static final long serialVersionUID = 1L;

	public RequestQuantityMoreException(String name) {
		this.generateObject("Quantidade insuficiente para o estoque de nome: "+ name +".",
				"A quantidade solicitada é a mais do que possui em estoque.");
	}
}
