package br.com.stock.exception;

public class StockMinimumException extends MainException{

	private static final long serialVersionUID = 1L;

	public StockMinimumException(){
		this.generateObject("O estoque minimo esta maior que o total que possui em estoque.",
				"Gentileza informar um valor que seja menor do que possui em estoque hoje.");
	}
}
