package br.com.stock.object;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class Request {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(length = 5)
	private int Id;
	
	/*@NotNull
	private int quantity;*/
	
	private int status;
	
	@Column(nullable = true, length = 45)
	private String reason;
	
	@Column(nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateRequest = new Date();
	
	@Column(nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateExit;
	
	@Column(nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateDelivery;
	
	@Column(nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date scheduleDate;
	
	@ManyToOne
	@JoinColumn(name = "UserId")
	private User user;
	
	/*@ManyToOne()
	@JoinColumn(name = "StockId")
	private Stock stock;*/
	
	/*@OneToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Stock> listStock;*/
	
	@OneToMany(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<RequestStockInfo> listInfo;
	
	@Column(nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date emissionDate;
	
	@Column(nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date entryDate;
	
	private String account;
	
	private String cr;
	
	private int documentNumber;
	
	private String project;
	
	private String costCenter;
	
	private int Branch;
	
	public int getBranch() {
		return Branch;
	}

	public void setBranch(int branch) {
		Branch = branch;
	}

	public Request(){
		/*User user = new User();
		user.setId(1);
		
		this.user = user;*/
	}
	
	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	/*public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}*/

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getDateRequest() {
		return dateRequest;
	}

	public void setDateRequest(Date dateRequest) {
		this.dateRequest = dateRequest;
	}

	public Date getDateExit() {
		return dateExit;
	}

	public void setDateExit(Date dateExit) {
		this.dateExit = dateExit;
	}

	public Date getDateDelivery() {
		return dateDelivery;
	}

	public void setDateDelivery(Date dateDelivery) {
		this.dateDelivery = dateDelivery;
	}

	public Date getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(Date scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/*public List<Stock> getListStock() {
		return listStock;
	}

	public void setListStock(List<Stock> listStock) {
		this.listStock = listStock;
	}*/
	
	/*public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}*/
	
	public List<RequestStockInfo> getListInfo() {
		return listInfo;
	}

	public void setListInfo(List<RequestStockInfo> listInfo) {
		this.listInfo = listInfo;
	}
	
	public Date getEmissionDate() {
		return emissionDate;
	}

	public void setEmissionDate(Date emissionDate) {
		this.emissionDate = emissionDate;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCr() {
		return cr;
	}

	public void setCr(String cr) {
		this.cr = cr;
	}

	public int getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(int documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}
	
	public String getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Id;
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
		Request other = (Request) obj;
		if (Id != other.Id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Request [Id=" + Id + ", status=" + status + ", reason="
				+ reason + ", dateRequest=" + dateRequest + ", dateExit="
				+ dateExit + ", dateDelivery=" + dateDelivery
				+ ", scheduleDate=" + scheduleDate + ", user=" + user
				+ ", listInfo=" + listInfo + ", emissionDate=" + emissionDate
				+ ", entryDate=" + entryDate
				+ "]";
	}
}
