package br.com.stock.bd.jpa;

import java.util.List;

import br.com.stock.bd.interfaces.SubCategoryDAO;
import br.com.stock.object.SubCategory;

public class SubCategoryJPADAO extends JPAAbstract<SubCategory,Integer> implements SubCategoryDAO{
	
	public List<SubCategory> getListFilterCategory(int idCategory) {
		return this.list("SELECT S FROM "+ this.getEntityName() +" S WHERE S.category.id = "+ idCategory);
	}
	
}
