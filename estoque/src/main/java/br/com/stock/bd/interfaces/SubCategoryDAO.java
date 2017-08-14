package br.com.stock.bd.interfaces;

import java.util.List;

import br.com.stock.object.SubCategory;

public interface SubCategoryDAO extends CrudDAO<SubCategory,Integer>{
	
	public List<SubCategory> getListFilterCategory(int idCategory);
}
