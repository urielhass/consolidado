package br.com.stock.filter;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.auth0.jwt.JWTExpiredException;

import br.com.stock.auth.Auth;
import br.com.stock.enums.UserPermission;
import br.com.stock.exception.UserAcessException;
import br.com.stock.util.PermissionControl;
import br.com.stock.util.UtilRest;

@PreMatching
@Priority(Priorities.AUTHORIZATION)
@Provider
public class interceptor extends UtilRest implements ContainerRequestFilter{
 
	@Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
		String url = requestContext.getUriInfo().getPath();

        if (!url.endsWith("/")) {
            url += "/";
        }
        
        if (requestContext.getMethod().equals("OPTIONS")) {
            return;
        }
        if (!url.equals("/login/auth/")) {
            String authorizationHeader = requestContext.getHeaderString("authorization");
            if (!url.equals("login/auth/")) {
                Auth auth = new Auth();
                try {
                	auth.validate(authorizationHeader.replace("Bearer ", ""));
                	int permission = Integer.parseInt(auth.permission(authorizationHeader.replace("Bearer ", "")));
                	
                	PermissionControl permissionControl = new PermissionControl();
                	
                	if(UserPermission.REQUEST.getValue() == permission){
                		permissionControl.request(url);
                	}else if(UserPermission.STOCK.getValue() == permission){
                		permissionControl.stock(url);
                	}
                	
				} catch (Exception e) {
                	requestContext.abortWith(this.getResponsePrivate());
				}

            } else {
                if (authorizationHeader != null) {}
            }
        }
       
    }
}