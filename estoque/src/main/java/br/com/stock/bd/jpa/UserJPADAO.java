package br.com.stock.bd.jpa;

import br.com.stock.bd.interfaces.UserDAO;
import br.com.stock.object.User;

public class UserJPADAO extends JPAAbstract<User,Integer> implements UserDAO{

	public int getEmailAmount(String email) {
		return this.list("SELECT U FROM "+ this.getEntityName() +" U WHERE U.email = '"+ email +"'").size();
	}
	
	public int getEmailAmount(String email, int id){
		return this.list("SELECT U FROM "+ this.getEntityName() +" U WHERE U.email = '"+ email +"' AND U.id != "+ id).size();
	}

	public User getObjectByEmail(String email) {
		return this.getObject("SELECT U FROM "+ this.getEntityName() +" U WHERE U.email = '"+ email +"'");
	}
}
