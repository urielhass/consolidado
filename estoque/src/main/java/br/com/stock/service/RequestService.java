package br.com.stock.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.stock.bd.interfaces.RequestDAO;
import br.com.stock.bd.interfaces.StockDAO;
import br.com.stock.bd.interfaces.UserDAO;
import br.com.stock.bd.jpa.DAOFactory;
import br.com.stock.enums.Status;
import br.com.stock.enums.UserPermission;
import br.com.stock.exception.RequestExceptionAwaitting;
import br.com.stock.exception.RequestExceptionDisapproved;
import br.com.stock.exception.RequestExceptionEnding;
import br.com.stock.exception.RequestExceptionUserAcess;
import br.com.stock.exception.RequestQuantityMoreException;
import br.com.stock.object.Request;
import br.com.stock.object.RequestStockInfo;
import br.com.stock.object.Stock;
import br.com.stock.object.User;
import br.com.stock.vo.ListRequest;

/**
 * Classe de servico para controle de requisicoes.
 * 
 * @author bruno_rogerio
 *
 */
public class RequestService extends ServiceAbstract<Request, Integer, RequestDAO>{

	private String emailUsuario;
	
	@Override
	public Request add(Request e) throws Exception {
		
		UserDAO userDao = (UserDAO) DAOFactory.getInstanceOf(UserDAO.class);
		User user = userDao.getObjectByEmail(emailUsuario);
		
		return this.addReq(e,user);
	}
	
	/**
	 * Esse Metodo seta o email do usuario logado.
	 * 
	 * @param emailUsuario- email do usuario.
	 */
	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
	}

	/*public ListRequest add(ListRequest e) throws Exception {
		
		int cont = 0;
		
		List<Request> listRequest = e.getListRequest();
		
		UserDAO userDao = (UserDAO) DAOFactory.getInstanceOf(UserDAO.class);
		User user = userDao.getObjectByEmail(emailUsuario);
		
		for(Request req : listRequest){
			Request reqCad = this.addReq(req,user);
			
			listRequest.set(cont, reqCad);
			
			cont++;
		}
		
		e.setListRequest(listRequest);
		
		return e;
	}*/
	
	/**
	 * Esse metodo realiza a validacao da quantidade informada pelo usuario. Se o usuario solicitar uma quantidade de livros acima
	 * do que tem em estoque o sistema barra a solicitacao.
	 * 
	 * @param requestInfo - informacoes da requisicao.
	 * @param em - conexao do banco de dados.
	 * @return retorna as informacoes atualizadas do estoque.
	 * @throws Exception - Caso o usuario informe uma quantidade acima do estoque, esse metodo lanca uma exception.
	 */
	private RequestStockInfo valQuantityStock(RequestStockInfo requestInfo, EntityManager em) throws Exception{
		
		StockDAO stockDao = (StockDAO) DAOFactory.getInstanceOf(StockDAO.class);
		
		Stock stock = stockDao.getObject(requestInfo.getStock().getId(), em);
		
		// Se a quantidade solicitada pelo usuario for maior da que tem em estoque o usuario e barrado.
		if(requestInfo.getQuantity() > stock.getQuantity()){
			throw new RequestQuantityMoreException(stock.getName());
		}
		
		requestInfo.setStock(stock);
		
		return requestInfo;
	}
	
	/**
	 * Esse Metodo cadatra as requisicoes.
	 * 
	 * @param request - informacoes da requisicao.
	 * @param user - usuario logado do sistema.
	 * @return retorna as informacoes da requisicao cadastrada.
	 * @throws Exception - lanca um erro em de inconsistencia na validacao.
	 */
	private Request addReq(Request request, User user) throws Exception{
		
		EntityManager em = this.getEntityManager();
		em.getTransaction().begin();
		
		try{
			List<RequestStockInfo> listInfo = new ArrayList<RequestStockInfo>();
			// Iniciando as validacoes das quantidades do stock na requisicao.
			for(RequestStockInfo requestInfo : request.getListInfo()){
				requestInfo = this.valQuantityStock(requestInfo, em);
				
				listInfo.add(requestInfo);
			}
			
			request.setListInfo(listInfo);
			
			//Mudando o status da requisicao.
			request.setStatus(Status.AWAITING.getValue());
			
			request.setUser(user);
			
			request = this.dao.add(request, em);
			
			em.getTransaction().commit();
			
			return request;
		}catch(Exception ex){
			em.getTransaction().rollback();
			throw ex;
		}finally{
			this.closeConection();
		}
	}

	/**
	 * Esse metodo lista as requisicoes cadastradas filtrando pelo usuario que a cadastrou.
	 * 
	 * @return uma lista de requisicoes.
	 * @throws Exception - lanca um erro em caso de erros de execucao.
	 */
	public List<Request> listRequestByUser() throws Exception{
		
		UserDAO userDao = (UserDAO) DAOFactory.getInstanceOf(UserDAO.class);
		User user = userDao.getObjectByEmail(emailUsuario);
		
		return this.dao.listRequestByUser(user.getId());
	}
	
	/**
	 * Metodo para listar as requisicoes pendentes.
	 * 
	 * @return uma lista de requisicoes.
	 * @throws Exception em caso de erros de execucao.
	 */
	public List<Request> listRequestPending() throws Exception{
		return this.dao.listRequestPending();
	}
	
	/**
	 * Metodo para listar as requisicoes aprovadas
	 * 
	 * @return lista de requisicoes aprovadas.
	 */
	public List<Request> listRequestApproved() {
		return this.dao.listRequestApproved();
	}
	
	/**
	 * Esse metodo realiza movimentacoes nos status das requisicoes.
	 * Dependendo do status atual em que se encontra o mesmo move para
	 * a proxima etapa.
	 * 
	 * @param id da requisicao.
	 * @throws Exception lanca um erro em caso de nao conformidade da
	 * requisicao ou erro.
	 */
	public void moveStatus(int id) throws Exception{
		
		Request request = this.dao.getObject(id);
		
		if(Status.DISAPPROVED.getValue() == request.getStatus()){
			throw new RequestExceptionDisapproved();
		}else if(Status.ENDING.getValue() == request.getStatus()){
			throw new RequestExceptionEnding();
		}else if(Status.AWAITING.getValue() == request.getStatus()){
			throw new RequestExceptionAwaitting();
		}else if(Status.APPROVED.getValue() == request.getStatus()){
			requestOnMyWay(request);
		}else if(Status.ONMYWAY.getValue() == request.getStatus()){
			requestAwaitingClosure(request);
		}else if(Status.AWAITINGCLOSURE.getValue() == request.getStatus()){
			requestEnding(request);
		}
	}
	
	/**
	 * Metodo que realiza a subtracao da quantidade do estoque quando uma requisicao e excluida.
	 * 
	 * @param reqInfo - Informacoes da requisicao.
	 * @param em - Conexao do banco de dados.
	 * @throws Exception - Lancada em caso de erros ou nao conformidades na regra.
	 */
	private void subtractAmount(RequestStockInfo reqInfo, EntityManager em) throws Exception{
		
		StockDAO stockDao = (StockDAO) DAOFactory.getInstanceOf(StockDAO.class);
		
		Stock stock = stockDao.getObject(reqInfo.getStock().getId(), em);
		
		stock.setQuantity(stock.getQuantity() - reqInfo.getQuantity());
		
		stockDao.edit(stock, em);
	}
	
	/**
	 * Metodo que altera o status da requisicao para a caminho.
	 * 
	 * @param request - a requisicao.
	 * @throws Exception - lancada em caso de erros ou nao conformidades.
	 */
	private void requestOnMyWay(Request request) throws Exception{
		
		/*UserDAO userDao = (UserDAO) DAOFactory.getInstanceOf(UserDAO.class);
		User user = userDao.getObjectByEmail(this.emailUsuario);*/
		
		/*if((user.getPermission() != UserPermission.REQUEST.getValue())&&(user.getPermission() != UserPermission.ADMIN.getValue())){
			throw new RequestExceptionUserAcess();
		}*/
		
		EntityManager em = this.getEntityManager();
		em.getTransaction().begin();
		
		try{
			/*Stock stock = request.getStock();
			
			stock.setQuantity(stock.getQuantity() - request.getQuantity());
			
			stockDao.edit(stock,em);*/
			
			/*for(Stock stock : request.getListStock()){
				stock.setQuantity(stock.getQuantity() - request.getQuantity());
				
				stockDao.edit(stock,em);
			}*/
			
			for(RequestStockInfo reqInfo: request.getListInfo()){
				this.subtractAmount(reqInfo, em);
			}
			
			/*request.setStatus(Status.AWAITINGCLOSURE.getValue());
			request.setDateExit(new Date());
			
			this.dao.edit(request,em);*/
			
			em.getTransaction().commit();
		}catch(Exception e){
			em.getTransaction().rollback();
			throw e;
		}
	}
	
	/**
	 * Metodo que movimenta o status da requisicao para aguardando encerramento.
	 * 
	 * @param request - a requisicao.
	 * @throws Exception - lancada em caso de erros ou nao conformidades.
	 */
	private void requestAwaitingClosure(Request request) throws Exception{
		
		UserDAO userDao = (UserDAO) DAOFactory.getInstanceOf(UserDAO.class);
		User user = userDao.getObjectByEmail(this.emailUsuario);
		
		if((user.getPermission() != UserPermission.REQUEST.getValue())&&(user.getPermission() != UserPermission.ADMIN.getValue())&&(user.getPermission() != UserPermission.STOCK.getValue())){
			throw new RequestExceptionUserAcess();
		}
		
		request.setStatus(Status.AWAITINGCLOSURE.getValue());
		request.setDateDelivery(new Date());
		
		this.dao.edit(request);
	}
	
	/**
	 * 
	 * @param request
	 * @throws Exception
	 */
	private void requestEnding(Request request) throws Exception{
		
		UserDAO userDao = (UserDAO) DAOFactory.getInstanceOf(UserDAO.class);
		User user = userDao.getObjectByEmail(emailUsuario);
		
		if(request.getUser().getId() != user.getId()){
			throw new RequestExceptionUserAcess();
		}
		
		request.setStatus(Status.ENDING.getValue());
		
		this.dao.edit(request);
	}

	public void approved(Request request) throws Exception{
		
		UserDAO userDao = (UserDAO) DAOFactory.getInstanceOf(UserDAO.class);

		User user = userDao.getObjectByEmail(this.emailUsuario);
		
		if((user.getPermission() != UserPermission.REQUEST.getValue())&&(user.getPermission() != UserPermission.ADMIN.getValue())){
			throw new RequestExceptionUserAcess();
		}
		
		this.requestOnMyWay(request);
		
		request.setStatus(Status.ONMYWAY.getValue());
		
		this.dao.edit(request);
	}
	
	public void disapproved(Request request) throws Exception{
		
		UserDAO userDao = (UserDAO) DAOFactory.getInstanceOf(UserDAO.class);
		User user = userDao.getObjectByEmail(this.emailUsuario);
		
		if((user.getPermission() != UserPermission.REQUEST.getValue())&&(user.getPermission() != UserPermission.ADMIN.getValue())){
			throw new RequestExceptionUserAcess();
		}
		
		request.setStatus(Status.DISAPPROVED.getValue());
		
		this.dao.edit(request);
	}
	
	public List<Request> listRequestFinish() {
		return this.dao.listRequestFinish();
	}
	
	public List<Request> listRequestByStatus(int status){
		return this.dao.listRequestByStatus(status);
	}
	
	public List<Request> listRequestAll(){
		
		UserDAO userDao = (UserDAO) DAOFactory.getInstanceOf(UserDAO.class);
		User user = userDao.getObjectByEmail(emailUsuario);
		
		return this.dao.listRequestAll(user);
	}

	@Override
	public void remove(Integer id) throws Exception {
		
		Request request = this.dao.getObject(id);
		StockDAO stockDao = (StockDAO) DAOFactory.getInstanceOf(StockDAO.class);
		
		if(request.getStatus() == Status.ONMYWAY.getValue()){
			for(RequestStockInfo info : request.getListInfo()){
				Stock stock = info.getStock();
				
				stock.setQuantity(stock.getQuantity() + info.getQuantity());
				
				stockDao.edit(stock);
			}
		}
		
		super.remove(id);
	}
	
	
}
