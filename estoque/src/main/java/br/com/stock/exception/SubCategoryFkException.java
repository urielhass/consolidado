package br.com.stock.exception;

public class SubCategoryFkException extends MainException{

	private static final long serialVersionUID = 1L;

	public SubCategoryFkException() {
		this.generateObject("Sub-Categoria vinculado a outros registros.",
				"Não é possivel excluir essa sub-categoria, pois a registros vinculados a mesma.");
	}
}
