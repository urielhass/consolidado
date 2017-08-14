package br.com.stock.bd.interfaces;

import java.util.List;

import br.com.stock.object.Request;
import br.com.stock.object.User;

public interface RequestDAO extends CrudDAO<Request,Integer>{
	
	public List<Request> listRequestPending();
	public List<Request> listRequestApproved();
	public List<Request> listRequestFinish();
	public List<Request> listRequestAll(User user);
	public List<Request> listRequestByStatus(int status);
	public List<Request> listRequestByUser(int id);
}
