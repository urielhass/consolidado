package br.com.stock.rest.abs;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import br.com.stock.service.ServiceBase;
import br.com.stock.util.UtilRest;

/**
 * 
 * Classe rest responsavel por receber as requisicoes e repassar a camada
 * service os dados.
 * 
 * @author bruno_rogerio
 * @version 1.0.0
 * @since 1.0.0
 *
 * @param <E>
 *            entity
 * @param <ID>
 *            Tipo da chave primaria
 * @param <SERVICE>
 *            classe service.
 */
public class RestAbstract<E, ID, SERVICE extends ServiceBase<E, ID>> extends UtilRest {

	/**
	 * Atributo que armazena o .class da entity.
	 */
	private Class<E> entity;

	/**
	 * Atributo que armazena o .class do service.
	 */
	protected Class<SERVICE> service;

	/**
	 * Atributo que armazena a instancia da classe UtilRest.
	 */

	@SuppressWarnings("unchecked")
	public RestAbstract() {

		@SuppressWarnings("rawtypes")
		Class<? extends RestAbstract> realClass = getClass();

		ParameterizedType superclass = (ParameterizedType) realClass
				.getGenericSuperclass();

		this.entity = (Class<E>) superclass.getActualTypeArguments()[0];
		this.service = (Class<SERVICE>) superclass.getActualTypeArguments()[2];
	}

	
	/**
	 * 
	 * Metodo que retorna um objeto que e o registro que consta na entity.
	 * 
	 * @param id
	 *            Id do registro da entity.
	 * @return Respota com o objeto da entity.
	 */
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Response getObject(@PathParam("id") ID id) {

		try {
			SERVICE service = this.service.newInstance();

			E e = service.getObject(id);

			return getResponseList(e);
		} catch (Exception e) {
			return getResponseError(e);
		}
	}

	/**
	 * 
	 * Metodo para fazer a listagem dos registros que se encontram na entity.
	 * 
	 * @return Resposta com um array list de registros da entity.
	 */
	@GET
	@Produces("application/json")
	public Response list() {

		try{
			SERVICE service = this.service.newInstance();

			List<E> e = service.list();

			return getResponseList(e);
		} catch (Exception e) {
			return getResponseError(e);
		}
	}

	/**
	 * 
	 * Metodo que insere determinada instancia de uma entity no banco de
	 * dados.
	 * 
	 * @param json
	 *            String json com os dados a serem cadastrados.
	 * @return resposta retornando se o cadastro foi bem sucedido ou nao.
	 */
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response add(String json) {

		try {
			E e = getObjectMapper().readValue(json, this.entity);

			SERVICE service = this.service.newInstance();

			return getResponseAdd(service.add(e));
		} catch (Exception e) {
			return getResponseError(e);
		}
	}

	/**
	 * 
	 * Metodo que realiza a edicao da instancia de uma entity no banco de
	 * dados.
	 * 
	 * @param json
	 *            String json com os dados a serem cadastrados.
	 * @return resposta retornando se a edicao foi bem sucedido ou nao.
	 */
	@PUT
	@Consumes("application/json")
	@Produces("application/json")
	public Response edit(String json) {

		try {
			E e = getObjectMapper().readValue(json, this.entity);

			SERVICE service = this.service.newInstance();

			return getResponseEdit(service.edit(e));
		} catch (Exception e) {
			return getResponseError(e);
		}
	}

	/**
	 * 
	 * Metodo que remove a instancia da entity no banco de dados.
	 * 
	 * @param id
	 *            Id do registro da entity.
	 * @return resposta retornando se a exclusao foi bem sucedido ou nao.
	 */
	@DELETE
	@Path("/{id}")
	@Produces("application/json")
	public Response remove(@PathParam("id") ID id) {

		try {
			SERVICE service = this.service.newInstance();

			service.remove(id);

			return getResponseRemove();
		} catch (Exception e) {
			return getResponseError(e);
		}
	}
}
