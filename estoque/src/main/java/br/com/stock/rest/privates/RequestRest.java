package br.com.stock.rest.privates;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import br.com.stock.auth.Auth;
import br.com.stock.object.Request;
import br.com.stock.rest.abs.RestAbstract;
import br.com.stock.service.RequestService;
import br.com.stock.vo.ListRequest;

@Path("/request")
public class RequestRest extends RestAbstract<Request, Integer, RequestService>{

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	@Override
	public Response add(String json) {
		try {
			Auth auth = new Auth();
			
			Request e = getObjectMapper().readValue(json, Request.class);

			RequestService service = this.service.newInstance();

			service.setEmailUsuario(auth.emailLogged(getHeaders("authorization")));
			
			return getResponseAdd(service.add(e));
		} catch (Exception e) {
			return getResponseError(e);
		}
	}
	
	@GET
	@Path("/listRequestByUser")
	@Produces("application/json")
	public Response listRequestByUser(){
		try{
			RequestService service = this.service.newInstance();
			Auth auth = new Auth();
			service.setEmailUsuario(auth.emailLogged(getHeaders("authorization")));
			return getResponseList(service.listRequestByUser());
		}catch(Exception e){
			return getResponseError(e);
		}
	}
	
	@GET
	@Path("/listRequestPending")
	@Produces("application/json")
	public Response listRequestPending(){
		try{
			RequestService service = this.service.newInstance();
			Auth auth = new Auth();
			service.setEmailUsuario(auth.emailLogged(getHeaders("authorization")));
			return getResponseList(service.listRequestPending());
		}catch(Exception e){
			return getResponseError(e);
		}
	}
	
	@GET
	@Path("/listRequestApproved")
	@Produces("application/json")
	public Response listRequestApproved(){
		try{
			RequestService service = this.service.newInstance();
			Auth auth = new Auth();
			service.setEmailUsuario(auth.emailLogged(getHeaders("authorization")));
			return getResponseList(service.listRequestApproved());
		}catch(Exception e){
			return getResponseError(e);
		}
	}
	@GET
	@Path("/listRequestByStatus/{id}")
	@Produces("application/json")
	@Consumes("application/json")
	public Response listRequestByStatus(@PathParam("id") int id){
		try{
		
			RequestService service = this.service.newInstance();
			Auth auth = new Auth();
			service.setEmailUsuario(auth.emailLogged(getHeaders("authorization")));
			return getResponseList(service.listRequestByStatus(id));
		}catch(Exception e){
			return getResponseError(e);
		}
	}
	@GET
	@Path("/listRequestFinish")
	@Produces("application/json")
	public Response listRequestFinish(){
		try{
			RequestService service = this.service.newInstance();
			Auth auth = new Auth();
			service.setEmailUsuario(auth.emailLogged(getHeaders("authorization")));
			return getResponseList(service.listRequestFinish());
		}catch(Exception e){
			return getResponseError(e);
		}
	}
	@GET
	@Path("/listRequestAll")
	@Produces("application/json")
	public Response listRequestAll(){
		try{
			RequestService service = this.service.newInstance();
			Auth auth = new Auth();
			service.setEmailUsuario(auth.emailLogged(getHeaders("authorization")));
			return getResponseList(service.listRequestAll());
		}catch(Exception e){
			return getResponseError(e);
		}
	}
	
	@PUT
	@Path("/moveStatus/{id}")
	@Produces("application/json")
	public Response moveStatus(@PathParam("id") int id){
		try{
			Auth auth = new Auth();
			
			RequestService service = this.service.newInstance();
			
			service.setEmailUsuario(auth.emailLogged(getHeaders("authorization")));
			
			service.moveStatus(id);
			
			return getResponseEdit(null);
		}catch(Exception e){
			return getResponseError(e);
		}
	}
	
	@PUT
	@Path("/approved")
	@Consumes("application/json")
	@Produces("application/json")
	public Response approved(String json){
		try{
			Request request = getObjectMapper().readValue(json, Request.class);
			Auth auth = new Auth();
			RequestService service = this.service.newInstance();
			
			service.setEmailUsuario(auth.emailLogged(getHeaders("authorization")));
			
			service.approved(request);
			
			return getResponseEdit(null);
		}catch(Exception e){
			return getResponseError(e);
		}
	}
	
	@PUT
	@Path("/disapproved")
	@Consumes("application/json")
	@Produces("application/json")
	public Response disapproved(String json){
		try{
			Request request = getObjectMapper().readValue(json, Request.class);
			
			RequestService service = this.service.newInstance();
			Auth auth = new Auth();
			service.setEmailUsuario(auth.emailLogged(getHeaders("authorization")));
			service.disapproved(request);
			
			return getResponseEdit(null);
		}catch(Exception e){
			return getResponseError(e);
		}
	}
}
