package br.com.stock.service;

import java.util.Date;
import java.util.List;

import br.com.stock.bd.interfaces.LogStockDAO;
import br.com.stock.bd.interfaces.StockDAO;
import br.com.stock.bd.interfaces.SubCategoryDAO;
import br.com.stock.bd.interfaces.UserDAO;
import br.com.stock.bd.jpa.DAOFactory;
import br.com.stock.exception.StockMinimumException;
import br.com.stock.object.Location;
import br.com.stock.object.LogStock;
import br.com.stock.object.MonthYear;
import br.com.stock.object.Stock;
import br.com.stock.vo.RequestHistoricVo;
import br.com.stock.vo.StockListVo;

public class StockService extends ServiceAbstract<Stock,Integer,StockDAO>{

	private String emailUsuario;
	
	public void setEmailUsuario(String nomeUsuario) {
		this.emailUsuario = nomeUsuario;
	}
	
	@Override
	public List<Stock> list() throws Exception {
		// TODO Auto-generated method stub
		return super.list();
	}

	public List<StockListVo> listTotal() throws Exception {
		return this.dao.listTotal();
	}
	
	@Override
	public Stock add(Stock e) throws Exception {
		
		UserDAO userDao = (UserDAO) DAOFactory.getInstanceOf(UserDAO.class);
		
		e.setUser(userDao.getObjectByEmail(this.emailUsuario));
		
		e.setQuantity(this.recalculatesTotal(e.getListLocation()));
		
		this.validMinimum(e.getQuantity(), e.getMinimum());
		
		Stock stock = super.add(e);
		
		SubCategoryDAO subCategoryDao = (SubCategoryDAO) DAOFactory.getInstanceOf(SubCategoryDAO.class);
		
		stock.setSubCategory(subCategoryDao.getObject(e.getSubCategory().getId()));
		
		return stock;
	}
	
	@Override
	public Stock edit(Stock e) throws Exception {
		
		UserDAO userDao = (UserDAO) DAOFactory.getInstanceOf(UserDAO.class);
		
		e.setUser(userDao.getObjectByEmail(this.emailUsuario));
		
		this.stockAdress(e);
		
		System.out.println(e.getEmissionDate());
		
		e.setQuantity(this.recalculatesTotal(e.getListLocation()));
		
		this.validMinimum(e.getQuantity(), e.getMinimum());
		
		return super.edit(e);
	}

	private void validMinimum(int total, int minimum) throws StockMinimumException{
// CASO O CHEFE QUEIRA VALIDACAO DE ESTOQUE MINIMO, DESCOMENTAR
//		if(minimum > total){
//			throw new StockMinimumException();
//		}
	}
	
	private int recalculatesTotal(List<Location> listLocation){
		
		int total = 0;
		
		for(Location location : listLocation){
			total = total + location.getQuantity();
		}
		
		return total;
	}
	
	private void stockAdress(Stock stock){
		
		int quantityPrevios = this.dao.getObject(stock.getId()).getQuantity();
		int diference = this.recalculatesTotal(stock.getListLocation()) - quantityPrevios;
		int absolute = 0;
		
		if(diference != 0){
			LogStock logStock = new LogStock();
			
			if(diference < 0){
				absolute = Math.abs(diference);
				logStock.setOperation("R");
			}else{
				absolute = diference;
				logStock.setOperation("A");
			}
			logStock.setDifference(absolute);
			
			logStock.setQuantityPrevious(quantityPrevios);
			
			logStock.setQuantityNext(this.recalculatesTotal(stock.getListLocation()));
			
			logStock.setStock(stock);
			
			LogStockDAO logStockDao = (LogStockDAO) DAOFactory.getInstanceOf(LogStockDAO.class);
			logStockDao.add(logStock);
		}
	}
	
	public List<Stock> listStockLow() throws Exception{
		return this.dao.listStockLow();
	}
	
	public String totalHistoric() throws Exception{
		return this.dao.totalHistoric();
	}
	
	public List<RequestHistoricVo> listHistoric(Date start, Date end) throws Exception{
		return this.dao.listHistoric(start, end);
	}
	
	public List<MonthYear> monthWithRequest() throws Exception{
		return this.dao.monthWithRequest();
	}

	public List<Stock> listStockByFilter(int id_category, int id_subcategory, String search) {
		return this.dao.listStockByFilter(id_category, id_subcategory, search);
	}
}
