package br.com.stock.bd.jpa;

import br.com.stock.bd.interfaces.CategoryDAO;
import br.com.stock.object.Category;

public class CategoryJPADAO extends JPAAbstract<Category,Integer> implements CategoryDAO{
	
	public int getNameCategory(String nome) {
		return this.list("SELECT U FROM "+ this.getEntityName() +" U WHERE U.name = '"+ nome +"'").size();
	}
	
	public int getNameCategory(String nome, Integer id) {
		return this.list("SELECT U FROM "+ this.getEntityName() +" U WHERE U.name = '"+ nome +"' and U.id <> '"+id+"'").size();
	}
	
}
