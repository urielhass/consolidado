package br.com.stock.bd.jpa;

import br.com.stock.bd.interfaces.LoginDAO;
import br.com.stock.object.User;

public class LoginJPADAO extends JPAAbstract<User, Integer> implements LoginDAO{

	@Override
	public int getEmailAmount(String email) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public User getObject(String email, String password) {
		return this.getObject("SELECT u FROM "+this.getEntityName()+" u WHERE u.email='"+email+"' AND u.password='"+password+"' AND u.status = true");
	}	
}
