package br.com.stock.bd.interfaces;

import br.com.stock.object.Category;

public interface CategoryDAO extends CrudDAO<Category,Integer>{
	
	public int getNameCategory(String nome);
	public int getNameCategory(String nome, Integer id);
}
