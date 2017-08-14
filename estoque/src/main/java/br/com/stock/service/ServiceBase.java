package br.com.stock.service;

import java.util.List;

/**
 * 
 * Interface basica de servico de regra de negocio reponsavel pelos cruds da
 * aplicacao.
 * 
 * @author bruno_rogerio
 *
 * @param <E>
 *            Entidade
 * @param <ID>
 *            Tipo da chave primaria
 */
public interface ServiceBase<E, ID> {

	/**
	 * 
	 * Metodo que retorna um objeto que e o registro que consta na entidade.
	 * 
	 * @param id
	 *            Id do registro da entidade.
	 * @return E objeto da entidade.
	 */
	public E getObject(ID id) throws Exception;

	/**
	 * 
	 * Metodo que insere determinada instancia de uma entidade no banco de dados
	 * 
	 * @param e
	 *            instancia da entidade
	 * @throws Exception
	 */
	public E add(E e) throws Exception;

	/**
	 * 
	 * Metodo que realiza a edicao da instancia de uma entidade no banco de
	 * dados.
	 * 
	 * @param e
	 *            instancia da entidade
	 */
	public E edit(E e) throws Exception;

	/**
	 * 
	 * Metodo que remove a instancia da entidade no banco de dados.
	 * 
	 * @param id
	 *            instancia do tipo da chave primaria do banco de dados.
	 */
	public void remove(ID id) throws Exception;

	/**
	 * 
	 * Metodo para fazer a listagem dos registros que se encontram na entidade.
	 * 
	 * @return List<E> retorna um array list de registros da entidade.
	 */
	public List<E> list() throws Exception;
}
