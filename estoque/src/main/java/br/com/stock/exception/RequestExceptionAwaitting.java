package br.com.stock.exception;

public class RequestExceptionAwaitting extends MainException{

	private static final long serialVersionUID = 1L;

	public RequestExceptionAwaitting() {
		this.generateObject("Esta requisição esta aguardando a aprovação dos superiores.",
				"Gentileza aguardar o retorno dos usuários administradores para poder realizar a movimentação desta requisição.");
	}
}
