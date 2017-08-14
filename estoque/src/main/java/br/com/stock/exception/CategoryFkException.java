package br.com.stock.exception;

public class CategoryFkException extends MainException{

	private static final long serialVersionUID = 1L;

	public CategoryFkException() {
		this.generateObject("Categoria vinculado a outros registros.",
				"Não é possivel excluir essa categoria, pois a registros vinculados a mesma.");
	}
}
