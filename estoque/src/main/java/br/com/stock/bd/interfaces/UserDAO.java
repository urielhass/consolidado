package br.com.stock.bd.interfaces;

import br.com.stock.object.User;

public interface UserDAO extends CrudDAO<User, Integer> {

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

	/**
	 * 
	 * Metodo utilizado pra quando for editar o usuario ele validar o email
	 * desconsiderando o email atual do usuario.
	 * 
	 * @param email
	 *            do usuario
	 * @param id
	 *            do usuario
	 * @return quantidades de usuarios que possuem aquele e-mail
	 */
	public int getEmailAmount(String email, int id);
	
	public User getObjectByEmail(String email);
}
