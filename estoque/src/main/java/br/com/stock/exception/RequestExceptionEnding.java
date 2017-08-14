package br.com.stock.exception;

public class RequestExceptionEnding extends MainException{
	
	private static final long serialVersionUID = 1L;

	public RequestExceptionEnding() {
		this.generateObject("Este requisição esta encerrada.",
				"Não é possivel realizar nehuma movimentação nessa requisição.");
	}
}
