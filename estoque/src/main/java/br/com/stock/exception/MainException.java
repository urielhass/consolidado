package br.com.stock.exception;

import br.com.stock.util.ObjectException;

/**
 * Classe padrao para lancamentos de excpetion de regra de negocio.
 * 
 * @author bruno_rogerio
 *
 */
public class MainException extends Exception{

	private ObjectException object;
	
	private static final long serialVersionUID = 1L;

	/**
	 * Metodo criado para retornar a configuracao da exception.
	 * 
	 * @return a configuracao do objeto.
	 */
	public ObjectException getObject() {
		return object;
	}

	/**
	 * Recebe o objeto de configuracao da exception.
	 * 
	 * @param object - objeto de configuraçao.
	 */
	private void setObject(ObjectException object) {
		this.object = object;
	}
	
	/**
	 * Gera um objeto de configuracao de acordo com os parametros passados.
	 * 
	 * @param msg - mensagem para mostrar ao cliente.
	 * @param descricao - descricao do erro.
	 */
	protected void generateObject(String msg, String descricao){
		
		ObjectException object = new ObjectException();
		
		object.setMessage(msg);
		object.setDescription(descricao);
		
		this.setObject(object);
	}
}
