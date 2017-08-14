package br.com.stock.bd.interfaces;

import br.com.stock.object.User;

public interface LoginDAO extends CrudDAO<User, Integer> {

	/**
	 * 
	 * Metodo que realiza uma consulta para quais os usuarios possuem aquele
	 * e-mail passado por parametro.
	 * 
	 * @param email
	 *            a ser procurado.
	 * @return quantidade de usuarios que possuem aquele email.
	 */
	public int getEmailAmount(String email);
	public User getObject(String email, String password);
}
