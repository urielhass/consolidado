package br.com.stock.service;

import br.com.stock.auth.Auth;
import br.com.stock.bd.interfaces.UserDAO;
import br.com.stock.exception.UserEmailExistingException;
import br.com.stock.object.User;
import br.com.stock.util.Encryption;

public class UserService extends ServiceAbstract<User, Integer, UserDAO>{

	@Override
	public User add(User e) throws Exception {
		
		int qty = this.dao.getEmailAmount(e.getEmail());
		if(qty > 0){
			throw new UserEmailExistingException();
		}
		
		e.setPassword(Encryption.md5(e.getPassword()));
		
		return super.add(e);
	}

	@Override
	public User edit(User e) throws Exception {
		
		int qty = this.dao.getEmailAmount(e.getEmail(),e.getId());
		if(qty > 0){
			throw new UserEmailExistingException();
		}
		
		if((e.getPassword() != null)&&(!e.getPassword().isEmpty())){
			e.setPassword(Encryption.md5(e.getPassword()));
		} else {
			User user = this.dao.getObjectByEmail(e.getEmail());
			e.setPassword(user.getPassword());
		}
		
		return super.edit(e);
	}
	
	public User editActualUser(String hash) throws Exception{
		Auth auth = new Auth();
		
		return this.dao.getObjectByEmail(auth.emailLogged(hash));
	}
	
	public void login(User e) throws Exception{
			System.out.println(e.getEmail());
	}
}
