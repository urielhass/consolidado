package br.com.stock.bd.interfaces;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.stock.object.MonthYear;
import br.com.stock.object.Stock;
import br.com.stock.vo.RequestHistoricVo;
import br.com.stock.vo.StockListVo;

public interface StockDAO extends CrudDAO<Stock, Integer> {

	List<Stock> listStockLow();
	int countStockBycategory(int idCategory);
	int countStockBySubCategory(int idSubCategory);
	List<StockListVo> listTotal();
	List<RequestHistoricVo> listHistoric(Date start, Date end);
	String totalHistoric();
	List<MonthYear> monthWithRequest();
	List<Stock> listStockByFilter(int id_category, int id_subcategory, String search);
}
