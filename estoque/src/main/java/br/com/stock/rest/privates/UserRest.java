package br.com.stock.rest.privates;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import br.com.stock.object.User;
import br.com.stock.rest.abs.RestAbstract;
import br.com.stock.service.UserService;

@Path("/user")
public class UserRest extends RestAbstract<User, Integer, UserService>{
	
	
	@GET
	@Path("/editActualUser/")
	@Produces("application/json")
	@Consumes("application/json")
	public Response editActualUser(){
		try{
			UserService service = this.service.newInstance();
			return getResponseList(service.editActualUser(getHeaders("Authorization")));
		}catch(Exception e){
			return getResponseError(e);
		}
	}
}
