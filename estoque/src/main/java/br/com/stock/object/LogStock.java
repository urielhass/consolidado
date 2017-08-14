package br.com.stock.object;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class LogStock {

	@Id
	@Column(length = 5)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	private Stock stock;
	
	@ManyToOne
	private User user;
	
	@Temporal(TemporalType.TIMESTAMP)

	private Date dateLog = new Date();
	
	@Column(length = 5)
	private int quantityPrevious;
	
	@Column(length = 5)
	private int quantityNext;

	@Column(length = 5)
	private int difference;
	
	@Column(length = 1)
	private String operation;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Date getDateLog() {
		return dateLog;
	}

	public void setDateLog(Date dateLog) {
		this.dateLog = dateLog;
	}

	public int getQuantityPrevious() {
		return quantityPrevious;
	}

	public void setQuantityPrevious(int quantityPrevious) {
		this.quantityPrevious = quantityPrevious;
	}

	public int getQuantityNext() {
		return quantityNext;
	}

	public void setQuantityNext(int quantityNext) {
		this.quantityNext = quantityNext;
	}

	public int getDifference() {
		return difference;
	}

	public void setDifference(int difference) {
		this.difference = difference;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LogStock other = (LogStock) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LogStock [id=" + id + ", stock=" + stock + ", user=" + user
				+ ", quantityPrevious=" + quantityPrevious + ", quantityNext="
				+ quantityNext + ", difference=" + difference + ", operation="
				+ operation + "]";
	}
}
