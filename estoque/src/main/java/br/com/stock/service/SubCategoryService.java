package br.com.stock.service;

import java.util.List;

import br.com.stock.bd.interfaces.StockDAO;
import br.com.stock.bd.interfaces.SubCategoryDAO;
import br.com.stock.bd.jpa.DAOFactory;
import br.com.stock.exception.SubCategoryFkException;
import br.com.stock.object.SubCategory;

public class SubCategoryService extends ServiceAbstract<SubCategory, Integer, SubCategoryDAO>{
	
	public List<SubCategory> getListFilterCategory(int idCategory){
		System.out.println(idCategory);
		return this.dao.getListFilterCategory(idCategory);
	}

	@Override
	public void remove(Integer id) throws Exception {
		
		StockDAO stockDao = (StockDAO) DAOFactory.getInstanceOf(StockDAO.class);
		
		int qte = stockDao.countStockBySubCategory(id);
		
		if(qte > 0){
			throw new SubCategoryFkException();
		}
		
		super.remove(id);
	}
}
