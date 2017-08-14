package br.com.stock.util;

import br.com.stock.exception.UserAcessException;

public class PermissionControl {
	
	/*public boolean admin(String path){
		String[] array = {"caminho1","caminho2","caminho3"};
		return this.filterArray(array, path);
	}*/
	
	public void stock(String path) throws UserAcessException{
		String[] array = {"stock/",
				"stock/listStockLow/",
				"request/",
				"unit/",
				"subCategory/filterCategory/",
				"category/",
				"container/",
				"request/listRequestPending/",
				"request/listRequestAll/",
				"request/listRequestByUser/",
				"stock/listTotal/",
				"request/listRequestByStatus/",
				"request/listRequestApproved/",
				"request/moveStatus/",
				"request/listRequestFinish/",
				"request/listRequestAll/",
				"request/moveStatus/",
				"user/editActualUser/"};
		
		if(!this.filterArray(array, path)){
			throw new UserAcessException();
		}
	}
	
	public void request(String path) throws UserAcessException{
		String[] array = {"request/",
				"request/moveStatus/",
				"request/listRequestByUser/",
				"request/listRequestAll/",
				"stock/",
				"user/editActualUser/"};
		
		if(!this.filterArray(array, path)){
			throw new UserAcessException();
		}
	}
	
	private boolean filterArray(String[] array, String src){
		
		String[] arraySrc = src.split("/");
		int cont = arraySrc.length;
		
		try{
			Integer.parseInt(arraySrc[1]);
			
			if(cont > 1){
				src = arraySrc[0] +"/";
			}
		}catch(Exception e){
			if(cont > 1){
				src = arraySrc[0] +"/"+ arraySrc[1] +"/";
			}
		}
		
		boolean retorno = false;
		
		for(String caminho: array){
			if(caminho.equals(src)){
				retorno = true;
				break;
			}
		}
		
		return retorno;
	}
}
