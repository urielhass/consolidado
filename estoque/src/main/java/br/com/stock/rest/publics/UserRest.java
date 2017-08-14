package br.com.stock.rest.publics;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/users")
public class UserRest extends Rest{

	public UserRest(){
		
	}
	
	@POST	
	@Consumes("application/*")
	public void addUser(String data){
		
	}
		
}
