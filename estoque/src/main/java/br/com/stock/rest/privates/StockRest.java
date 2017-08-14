package br.com.stock.rest.privates;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.text.SimpleDateFormat;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import br.com.stock.auth.Auth;
import br.com.stock.object.Stock;
import br.com.stock.rest.abs.RestAbstract;
import br.com.stock.service.StockService;

@Path("/stock")
public class StockRest extends RestAbstract<Stock,Integer,StockService>{
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	@Override
	public Response add(String json) {
		
		try {
			Auth auth = new Auth();
			 
			SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-mm-dd");
			
			Stock e = getObjectMapper().setDateFormat(dt1).readValue(json, Stock.class);
			System.out.println(e);
			StockService service = this.service.newInstance();
			
			service.setEmailUsuario(auth.emailLogged(getHeaders("authorization")));
			
			return getResponseAdd(service.add(e));
		} catch (Exception e) {
			return getResponseError(e);
		}
	}
	
	@PUT
	@Consumes("application/json")
	@Produces("application/json")
	@Override
	public Response edit(String json) {
		
		try {
			Auth auth = new Auth();
			
			SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-mm-dd");
			
			Stock e = getObjectMapper().setDateFormat(dt1).readValue(json, Stock.class);


			StockService service = new StockService();

			service.setEmailUsuario(auth.emailLogged(getHeaders("authorization")));
			
			return getResponseEdit(service.edit(e));
		} catch (Exception e) {
			return getResponseError(e);
		}
	}

	@GET
	@Path("/listStockByFilter")
	@Produces("application/json")
	public Response listStockByFilter(@QueryParam("id_category") int id_category,
			@QueryParam("id_subcategory") int id_subcategory,
			@QueryParam("search") String search){
		try{
			StockService service = this.service.newInstance();
			
			return getResponseList(service.listStockByFilter(id_category, id_subcategory, search));
		}catch(Exception e){
			return getResponseError(e);
		}
	}

	@GET
	@Path("/listStockLow")
	@Produces("application/json")
	public Response listStockLow(){
		try{
			StockService service = this.service.newInstance();
			
			return getResponseList(service.listStockLow());
		}catch(Exception e){
			return getResponseError(e);
		}
	}
	
	@GET
	@Path("/listTotal")
	@Produces("application/json")
	public Response listTotal(){
		try{
			StockService service = this.service.newInstance();
			
			return getResponseList(service.listTotal());
		}catch(Exception e){
			return getResponseError(e);
		}
	}
	
	@GET
	@Path("/listHistoric")
	@Produces("application/json")
	public Response listHistoric(@QueryParam("start") String startString, @QueryParam("end") String endString){
		try{
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
			dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			
			Date start = null;
			Date end = null;
			
			if((startString != null && !startString.isEmpty())&&(endString != null && !endString.isEmpty())){
				start = dateFormat.parse(startString);
				end = dateFormat.parse(endString);
			}else{
				
				Date convertedDate = new Date();
				Calendar cStart = Calendar.getInstance();
				cStart.setTime(convertedDate);
				cStart.set(Calendar.DAY_OF_MONTH, cStart.getActualMinimum(Calendar.DAY_OF_MONTH));
				cStart.set(Calendar.HOUR_OF_DAY, 0);
				cStart.set(Calendar.MINUTE, 0);
				cStart.set(Calendar.SECOND, 0);
				
				start = cStart.getTime();
				
				Calendar cEnd = Calendar.getInstance();
				cEnd.setTime(convertedDate);
				cEnd.set(Calendar.DAY_OF_MONTH, cStart.getActualMaximum(Calendar.DAY_OF_MONTH));
				cStart.set(Calendar.HOUR_OF_DAY, 23);
				cStart.set(Calendar.MINUTE, 59);
				cStart.set(Calendar.SECOND, 59);
				end = cEnd.getTime();
			}
			
			StockService service = this.service.newInstance();
			
			return getResponseList(service.listHistoric(start,end));
		}catch(Exception e){
			return getResponseError(e);
		}
	}
	
	@GET
	@Path("/totalHistoric")
	@Produces("application/json")
	public Response totalHistoric(){
		try{
			StockService service = this.service.newInstance();
			
			return getResponseList(service.totalHistoric());
		}catch(Exception e){
			return getResponseError(e);
		}
	}
	
	@GET
	@Path("/monthWithRequest")
	@Produces("application/json")
	public Response monthWithRequest(){
		try{
			StockService service = this.service.newInstance();
			
			return getResponseList(service.monthWithRequest());
		}catch(Exception e){
			return getResponseError(e);
		}
	}
}
