package br.com.stock.bd.jpa;

import br.com.stock.bd.interfaces.CategoryDAO;
import br.com.stock.bd.interfaces.CityDAO;
import br.com.stock.bd.interfaces.ContainerDAO;
import br.com.stock.bd.interfaces.LocationDAO;
import br.com.stock.bd.interfaces.LogStockDAO;
import br.com.stock.bd.interfaces.LoginDAO;
import br.com.stock.bd.interfaces.RequestDAO;
import br.com.stock.bd.interfaces.StateDAO;
import br.com.stock.bd.interfaces.StockDAO;
import br.com.stock.bd.interfaces.SubCategoryDAO;
import br.com.stock.bd.interfaces.UnitDAO;
import br.com.stock.bd.interfaces.UserDAO;

public class DAOFactory {

	@SuppressWarnings("rawtypes")
	public static Object getInstanceOf(Class c){
		
		if(c.equals(UserDAO.class)){
			return new UserJPADAO();
		}else if(c.equals(StateDAO.class)){
			return new StateJPADAO();
		}else if(c.equals(CityDAO.class)){
			return new CityJPADAO();
		}else if(c.equals(CategoryDAO.class)){
			return new CategoryJPADAO();
		}else if(c.equals(LocationDAO.class)){
			return new LocationJPADAO();
		}else if(c.equals(RequestDAO.class)){
			return new RequestJPADAO();
		}else if(c.equals(StockDAO.class)){
			return new StockJPADAO();
		}else if(c.equals(SubCategoryDAO.class)){
			return new SubCategoryJPADAO();
		}else if(c.equals(UnitDAO.class)){
			return new UnitJPADAO();
		}else if(c.equals(LoginDAO.class)){
			return new LoginJPADAO();
		}else if(c.equals(ContainerDAO.class)){
			return new ContainerJPADAO();
		}else if(c.equals(LogStockDAO.class)){
			return new LogStockJPADAO();
		}else{
			return null;
		}
	}
	
	
}
