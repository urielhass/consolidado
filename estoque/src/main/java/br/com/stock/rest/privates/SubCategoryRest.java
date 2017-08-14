package br.com.stock.rest.privates;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import br.com.stock.object.SubCategory;
import br.com.stock.rest.abs.RestAbstract;
import br.com.stock.service.SubCategoryService;

@Path("/subCategory")
public class SubCategoryRest extends RestAbstract<SubCategory, Integer, SubCategoryService>{
	
	@GET
	@Path("/filterCategory/{id}")
	@Produces("application/json")
	public Response getListFilterCategory(@PathParam("id") int idCategory){
		
		try {
			SubCategoryService service = this.service.newInstance();
			
			return getResponseList(service.getListFilterCategory(idCategory));
		} catch (Exception e) {
			return getResponseError(e);
		}
	}
}
