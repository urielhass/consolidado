package br.com.stock.util;

import br.com.stock.exception.MainException;

public class RestResponse {

	private String message;
	private String description;
	private Object data;
	
	public RestResponse(Exception e){
		
		if(e instanceof MainException){
			this.RestResponseMainEx((MainException) e);
		}else{
			this.RestResponseEx(e);
		}
	}
	
	public RestResponse(String message, String description, Object data) {
		super();
		this.message = message;
		this.description = description;
		this.data = data;
	}

	private void RestResponseEx(Exception e){
		e.printStackTrace();
		this.message = "Erro no servidor";
		this.description = "Gentileza entrar em contato com o desenvolvedor.";
		this.data = null;
	}
	
	private void RestResponseMainEx(MainException e){
		ObjectException obj = e.getObject();
		
		this.message = obj.getMessage();
		this.description = obj.getDescription();
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
