package br.com.stock.service;

import javax.persistence.NoResultException;

import br.com.stock.auth.Auth;
import br.com.stock.bd.interfaces.LoginDAO;
import br.com.stock.exception.AuthException;
import br.com.stock.object.User;
import br.com.stock.util.Encryption;

public class LoginService extends ServiceAbstract<User, Integer, LoginDAO>{
	
	public User auth(User e) throws Exception, NoResultException{
		try{
			Encryption descrpt = new Encryption();
			Auth hashAuth = new Auth();
			e.setPassword(descrpt.md5(descrpt.bs64d(e.getPassword())));
			User user = this.dao.getObject(e.getEmail(), e.getPassword());
			if(user != null){
				user.setToken(hashAuth.generate(user.getEmail(), user.getPermission()));
				if(user.getAdm()){
					user.setAdm(true);
				}
				return user;
			}
			throw new AuthException();
		}catch(NoResultException AE){
			throw new AuthException();
		}
	}
}
