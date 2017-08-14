package br.com.stock.exception;

public class CategoryExistingException extends MainException {

	private static final long serialVersionUID = 1L;

	public CategoryExistingException() {
		this.generateObject("Categoria existente.",
				"Gentileza informar uma categoria que não esteja cadastrada no sistema.");
	}
}
