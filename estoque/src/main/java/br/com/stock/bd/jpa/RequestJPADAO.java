package br.com.stock.bd.jpa;

import java.util.List;

import br.com.stock.bd.interfaces.RequestDAO;
import br.com.stock.enums.Status;
import br.com.stock.enums.UserPermission;
import br.com.stock.object.Request;
import br.com.stock.object.User;

public class RequestJPADAO extends JPAAbstract<Request,Integer> implements RequestDAO{
	
	@Override
	public List<Request> list() {
		return this.list("SELECT R FROM "+ this.getEntityName() +" R");
	}

	public List<Request> listRequestPending(){
		return this.list("SELECT R FROM "+ this.getEntityName() +" R WHERE R.status = "+ Status.AWAITING.getValue());
	}

	public List<Request> listRequestApproved() {
		return this.list("SELECT R FROM "+ this.getEntityName() +" R"
				+ " WHERE R.status IN("+ Status.APPROVED.getValue() +","+ Status.ONMYWAY.getValue() +","+ Status.AWAITINGCLOSURE.getValue() +")");
	}
	
	public List<Request> listRequestFinish(){
		return this.list("SELECT R FROM "+this.getEntityName()+ " R"
				+ " WHERE R.status IN("+ Status.AWAITINGCLOSURE.getValue()+")");
	}
	
	public List<Request> listRequestByStatus(int status){
		return this.list("SELECT R FROM "+this.getEntityName()+ " R"
				+ " WHERE R.status IN("+ status +")");
	}
	
	public List<Request> listRequestAll(User user){
		
		String jpql = "SELECT R FROM "+ this.getEntityName() +" R";
		
		if(user.getPermission() != UserPermission.ADMIN.getValue()){
			jpql += " WHERE R.user.Id = "+ user.getId();
		}
		
		return this.list(jpql);
	}

	public List<Request> listRequestByUser(int id) {
		return this.list("SELECT R FROM "+this.getEntityName()+" R WHERE R.user.Id = "+id +" AND R.status NOT IN("+ Status.DISAPPROVED.getValue() +","+ Status.ENDING.getValue() +")");
	}
}
