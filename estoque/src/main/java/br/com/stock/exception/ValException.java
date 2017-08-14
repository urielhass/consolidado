package br.com.stock.exception;

public class ValException extends MainException{

	private static final long serialVersionUID = 1L;

	public ValException(String msg){
		this.generateObject(msg, "Gentileza preencher os dados corretamente.");
	}
}
