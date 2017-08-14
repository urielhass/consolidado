package br.com.stock.bd.jpa;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import br.com.stock.bd.interfaces.StockDAO;
import br.com.stock.enums.Status;
import br.com.stock.object.MonthYear;
import br.com.stock.object.Stock;
import br.com.stock.vo.RequestHistoricVo;
import br.com.stock.vo.StockListVo;

public class StockJPADAO extends JPAAbstract<Stock,Integer> implements StockDAO {
	public List<Stock> listStockLow(){
		return this.list("SELECT S FROM "+this.getEntityName()+ " S WHERE S.quantity < S.minimum");
	}
		
	public int countStockBycategory(int idCategory){
		
		String jpql = "SELECT E.cod AS ALGUM_VALOR FROM "+this.getEntityName()+" E WHERE E.subCategory.category.Id = "+ idCategory;
		
		EntityManager em = getEntityManager();
		
		TypedQuery<String> sql = em.createQuery(jpql, String.class);
		
		List<String> list = sql.getResultList();
		
		this.closeConection();
		
		return list.size();
	}
	
	public int countStockBySubCategory(int idSubCategory){
		
		String jpql = "SELECT E.cod AS ALGUM_VALOR FROM "+this.getEntityName()+" E WHERE E.subCategory.Id = "+ idSubCategory;
		
		EntityManager em = getEntityManager();
		
		TypedQuery<String> sql = em.createQuery(jpql, String.class);
		
		List<String> list = sql.getResultList();
		
		this.closeConection();
		
		return list.size();
	}
	
	public List<StockListVo> listTotal(){
		/*return this.listNativeQuery("SELECT STK.Id, STK.cod, CATE.description category, SUB.description subcategory, STK.description, STK.invoice, STK.quantity, STK.unitaryValue "
				+ "FROM stock STK INNER JOIN subcategory SUB ON(STK.SubCategoryId = SUB.Id) "
				+ "INNER JOIN category CATE ON(SUB.CategoryId = CATE.Id)");*/
		String sql = "SELECT STK.Id, STK.cod, STK.title, SUB.name subCategory, CATE.name category, STK.description, STK.invoice, STK.quantity, STK.unitaryValue "
				+ "FROM "+ this.getEntityName() +" STK INNER JOIN SubCategory SUB ON(STK.SubCategoryId = SUB.Id) "
				+ "INNER JOIN Category CATE ON(SUB.CategoryId = CATE.Id)";
		
		EntityManager em = getEntityManager();
		
		Query query = em.createNativeQuery(sql);
		
		List<StockListVo> listStock = new ArrayList<StockListVo>();
		
		@SuppressWarnings("unchecked")
		List<Object[]> list = query.getResultList();
		
		for(Object[] obj: list){
			StockListVo stock = new StockListVo();
			
			stock.setId((int)obj[0]);
			stock.setCod((String)obj[1]);
			stock.setTitle((String)obj[2]);
			stock.setSubCategory((String)obj[3]);
			stock.setCategory((String)obj[4]);
			stock.setDescription((String)obj[5]);
			stock.setInvoice((int)obj[6]);
			stock.setQuantity((int)obj[7]);
			stock.setUnitaryValue((double)obj[8]);

			listStock.add(stock);
		}
		
		this.closeConection();
		
		return listStock;
	}

	@Override
	public List<RequestHistoricVo> listHistoric(Date start, Date end) {
		
		//String sql = "SELECT STK.branch, STK.title stock, STK.unitaryValue, STK.enterDateInvoice entryDate, STK.invoice documentNumber FROM stock STK";
		/*String sql = "SELECT STK.branch, STK.title stock, STK.unitaryValue, REQ.dateRequest entryDate, REQ.invoice documentNumber, REQ.quantity, REQ.emissionDate emissionDate, UNT.costCenter costCenter FROM request REQ LEFT JOIN stock STK ON(REQ.StockId = STK.Id) LEFT JOIN user USR ON(REQ.UserId = USR.Id) LEFT JOIN userhasunit UHU ON(UHU.userId = USR.Id) LEFT JOIN unit UNT ON(UHU.unitId = UNT.Id)"
				+ " WHERE REQ.status = ? AND REQ.dateRequest >= ?  AND REQ.dateRequest <= ? ";*/
		
		String sql = "SELECT REQ.Branch, STK.title stock, STK.unitaryValue, REQ.dateRequest entryDate, "
				+ " STK.invoice documentNumber, RINFO.quantity, STK.emissionDate emissionDate, REQ.costCenter costCenter, "
				+ "STK.cpa cpa, STK.printShop printShop, REQ.project, STK.enterDateInvoice "
				+ "FROM "+ this.getEntityName() +" STK INNER JOIN RequestStockInfo RINFO ON (RINFO.StockId = STK.Id) "
				+ "INNER JOIN Request_RequestStockInfo RRINFO ON (RINFO.id = RRINFO.listInfo_id) "
				+ "INNER JOIN Request REQ ON (REQ.Id = RRINFO.Request_Id) "
				+ "WHERE REQ.status = ? AND REQ.dateRequest >= ?  AND REQ.dateRequest <= ?";
		
		EntityManager em = getEntityManager();
		
		Query query = em.createNativeQuery(sql);
		query.setParameter(1, Status.ENDING.getValue());
		query.setParameter(2, start, TemporalType.DATE);
		query.setParameter(3, end, TemporalType.DATE);
		
		List<RequestHistoricVo> listStock = new ArrayList<RequestHistoricVo>();
		
		@SuppressWarnings("unchecked")
		List<Object[]> list = query.getResultList();
		
		for(Object[] obj: list){
			RequestHistoricVo stock = new RequestHistoricVo();
			
			stock.setBranch((int)obj[0]);
			stock.setStock((String)obj[1]);
			stock.setUnitaryValue((double)obj[2] * (int)obj[5]);
			stock.setEntryDate((Date)obj[3]);
			stock.setDocumentNumber((int)obj[4]);
			stock.setQuantity((int)obj[5]);
			stock.setEmissionDate((Date)obj[6]);
			stock.setCostCenter((String)obj[7]);
			stock.setCpa((String)obj[8]);
			stock.setPrintShop((String)obj[9]);
			stock.setProject((String)obj[10]);
			stock.setEnterDateInvoice((Date)obj[11]);
			
			listStock.add(stock);
		}
		
		return listStock;
	}
	
	@Override
	public String totalHistoric() {
		
		String sql = "SELECT sum(quantity * unitaryValue) as vlr FROM "+this.getEntityName() +" WHERE quantity > 0 AND unitaryValue > 0;";
		
		EntityManager em = getEntityManager();
		
		Query query = em.createNativeQuery(sql);
		
		@SuppressWarnings("unchecked")
		String stock = query.getSingleResult().toString();
		
		return stock;
	}
	
	@Override
	public List<MonthYear> monthWithRequest() {
		
		String sql = "SELECT  Year(`dateDelivery`),  Month(`dateDelivery`),  Count(*) As requests FROM Request WHERE Year(`dateDelivery`) IS NOT NULL GROUP BY Year(`dateDelivery`), Month(`dateDelivery`)";
		
		EntityManager em = getEntityManager();
		
		Query query = em.createNativeQuery(sql);
		List<MonthYear> monthYears = new ArrayList<MonthYear>();
		@SuppressWarnings("unchecked")
		List<Object[]> list = query.getResultList();
		for(Object[] obj: list){
			MonthYear monthYear = new MonthYear();
			int year = (int) obj[0];
			int month = (int) obj[1];
			BigInteger id = (BigInteger) obj[2];
			monthYear.setMonth("" + month);
			monthYear.setYear("" + year);
			
			monthYears.add(monthYear);
		}
		return monthYears;
	}

	@Override
	public List<Stock> listStockByFilter(int id_category, int id_subcategory, String search) {
		
		if(search != null && !search.isEmpty()){
			search = search.toUpperCase();
		}else{
			search = "";
		}
		
		String jpql = "SELECT S FROM "+ this.getEntityName() +" S WHERE UPPER(S.title) LIKE '%"+ search +"%'";
//				+ " OR S.subCategory.category.id LIKE '%"+ search +"%'"
//				+ "OR S.subCategory.id LIKE "+ sub +"";
		
		// Filtrando a categoria.
		if(id_category > 0){
			jpql += " AND S.subCategory.category.id = "+ id_category;
		}
		
		// Filtrando a sub categoria.
		if(id_subcategory > 0){
			jpql += " AND S.subCategory.id = "+ id_subcategory;
		}
		
		return this.list(jpql);
	}
}