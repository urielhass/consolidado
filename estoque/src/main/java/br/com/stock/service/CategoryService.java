package br.com.stock.service;

import br.com.stock.bd.interfaces.CategoryDAO;
import br.com.stock.bd.interfaces.StockDAO;
import br.com.stock.bd.jpa.DAOFactory;
import br.com.stock.exception.CategoryExistingException;
import br.com.stock.exception.CategoryFkException;
import br.com.stock.object.Category;

public class CategoryService extends ServiceAbstract<Category, Integer, CategoryDAO>{
	
	@Override
	public Category add(Category c) throws Exception {
		
		int qtde = this.dao.getNameCategory(c.getName());
		if(qtde > 0){
			throw new CategoryExistingException();
		}
		
		return super.add(c);
	}
	
	
	@Override
	public Category edit(Category c) throws Exception {
		
		int qtde = this.dao.getNameCategory(c.getName(), c.getId());
		if(qtde > 0){
			throw new CategoryExistingException();
		}
		
		return super.edit(c);
	}


	@Override
	public void remove(Integer id) throws Exception {
		
		StockDAO stockDao = (StockDAO) DAOFactory.getInstanceOf(StockDAO.class);
		
		int qte = stockDao.countStockBycategory(id);
		
		if(qte > 0){
			throw new CategoryFkException();
		}
		
		super.remove(id);
	}
}
