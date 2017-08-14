package br.com.stock.service;

import java.lang.reflect.ParameterizedType;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import br.com.stock.bd.connection.JPAConnection;
import br.com.stock.bd.interfaces.CrudDAO;
import br.com.stock.bd.jpa.DAOFactory;
import br.com.stock.exception.ValException;

/**
 * 
 * Classe que realiza a implantacao da interface service base.
 * 
 * @author bruno_rogerio
 * @version 1.0.0
 * @since 1.0.0
 *
 * @param <E>
 *            Entidade
 * @param <ID>
 *            Tipo de chave primaria
 * @param <DAO>
 *            Interface DAO
 */
public class ServiceAbstract<E, ID, DAO extends CrudDAO<E, ID>> extends JPAConnection implements
		ServiceBase<E, ID> {

	/**
	 * Atributo que armazena a instancia da classe DAO.
	 */
	protected DAO dao;

	@SuppressWarnings("unchecked")
	public ServiceAbstract(){

		@SuppressWarnings("rawtypes")
		Class<? extends ServiceAbstract> realClass = getClass();

		ParameterizedType superclass = (ParameterizedType) realClass
				.getGenericSuperclass();

		Class<DAO> dao = (Class<DAO>) superclass.getActualTypeArguments()[2];

		this.dao = (DAO) DAOFactory.getInstanceOf(dao);
	}

	/**
	 * 
	 * Metodo para validar a entidade que esta sendo recebida por parametro.
	 * 
	 * @param e
	 * @throws Exception
	 */
	private void valEntity(E e) throws ValException {

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<E>> constraintViolations = validator
				.validate(e);
		if (constraintViolations.size() > 0) {
			Iterator<ConstraintViolation<E>> iterator = constraintViolations
					.iterator();

			String msg = "";

			while (iterator.hasNext()) {
				ConstraintViolation<E> cv = iterator.next();

				msg += "- " + cv.getMessage() +" \r";
			}

			throw new ValException(msg);
		}
	}

	/**
	 * 
	 * Implementacao do metodo que retorna um objeto que e o registro que consta
	 * na entidade.
	 * 
	 * @param id
	 *            Id do registro da entidade.
	 * @return E objeto da entidade.
	 */
	public E getObject(ID id) throws Exception {
		return this.dao.getObject(id);
	}

	/**
	 * 
	 * Implementacao do metodo que insere determinada instancia de uma entidade
	 * no banco de dados
	 * 
	 * @param e
	 *            instancia da entidade
	 * @throws Exception
	 *             erro de banco ou regra de negocio.
	 */
	public E add(E e) throws Exception {

		this.valEntity(e);

		return this.dao.add(e);
	}

	/**
	 * 
	 * Implementacao do metodo que realiza a edicao da instancia de uma entidade
	 * no banco de dados.
	 * 
	 * @param e
	 *            instancia da entidade
	 * @throws Exception
	 *             erro de banco ou regra de negocio.
	 */
	public E edit(E e) throws Exception {

		this.valEntity(e);

		return this.dao.edit(e);
	}

	/**
	 * 
	 * Implementacao do metodo que remove a instancia da entidade no banco de
	 * dados.
	 * 
	 * @param id
	 *            instancia do tipo da chave primaria do banco de dados.
	 */
	public void remove(ID id) throws Exception {
		this.dao.remove(id);
	}

	/**
	 * 
	 * Implementacao do metodo para fazer a listagem dos registros que se
	 * encontram na entidade.
	 * 
	 * @return List<E> retorna um array list de registros da entidade.
	 */
	public List<E> list() throws Exception {
		return this.dao.list();
	}
}
