package br.com.stock.exception;

public class RequestExceptionDisapproved extends MainException{

	private static final long serialVersionUID = 1L;

	public RequestExceptionDisapproved() {
		this.generateObject("Este requisição esta reprovada.",
				"Não é possivel realizar nehuma movimentação nessa requisição.");
	}
}
