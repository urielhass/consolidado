package br.com.stock.rest.privates;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import br.com.stock.object.User;
import br.com.stock.rest.abs.RestAbstract;
import br.com.stock.service.LoginService;



@Path("/login")
public class LoginRest extends RestAbstract<User, Integer, LoginService> {

	@POST
	@Path("/auth")
	@Consumes("application/*")
	public Response auth(String json)  {
		try {
			User e = getObjectMapper().readValue(json, User.class);

			LoginService service = this.service.newInstance();
			return getResponseList(service.auth(e));
		} catch (Exception e) {
			return getResponseError(e);
		}
		
	}
}
