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

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Stock{
	
	@Id
	@Column(length = 5)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int Id;
	
	@NotEmpty(message = "O campo código é obrigatório.")
	@Column(length = 45)
	private String cod;
	
	//@NotEmpty(message = "O campo nome é obrigatório.")
	@Column(length = 99)
	private String name;
	
	@NotEmpty(message = "O campo titulo é obrigatório.")
	@Column(length = 99)
	private String title;
	
	//@NotEmpty(message = "O campo descrição é obrigatório.")
	//@Lob
	private String description;
//	
//	@NotEmpty
	private int quantity;
	
	private int minimum = 35;
	
	private int invoice;
	
	private String invoiceResp;
	
	
	private Date enterDateInvoice;

	private Date emissionDate;
	
	private String branch;
	
	public String getInvoiceResp() {
		return invoiceResp;
	}

	public void setInvoiceResp(String invoiceResp) {
		this.invoiceResp = invoiceResp;
	}

	public Date getEnterDateInvoice() {
		return enterDateInvoice;
	}

	public void setEnterDateInvoice(Date enterDateInvoice) {
		this.enterDateInvoice = enterDateInvoice;
	}

	public Date getEmissionDate() {
		return emissionDate;
	}

	public void setEmissionDate(Date emissionDate) {
		this.emissionDate = emissionDate;
	}
	
	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	//	@NotEmpty
	private double unitaryValue;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "UserId")
	private User user;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "SubCategoryId")
	private SubCategory subCategory;
	
	@OneToMany(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Location> listLocation;

	private String cpa;
	
	private String printShop;
	
	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public int getMinimum() {
		return minimum;
	}

	public void setMinimum(int minimum) {
		this.minimum = minimum;
	}
	
	public int getInvoice() {
		return invoice;
	}

	public void setInvoice(int invoice) {
		this.invoice = invoice;
	}

	public double getUnitaryValue() {
		return unitaryValue;
	}

	public void setUnitaryValue(double unitaryValue) {
		this.unitaryValue = unitaryValue;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public SubCategory getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}
	
	public List<Location> getListLocation() {
		return listLocation;
	}

	public void setListLocation(List<Location> listLocation) {
		this.listLocation = listLocation;
	}
	
	public String getCpa() {
		return cpa;
	}

	public void setCpa(String cpa) {
		this.cpa = cpa;
	}

	public String getPrintShop() {
		return printShop;
	}

	public void setPrintShop(String printShop) {
		this.printShop = printShop;
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
		Stock other = (Stock) obj;
		if (Id != other.Id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Stock [Id=" + Id + ", cod=" + cod + ", name=" + name
				+ ", title=" + title + ", description=" + description
				+ ", quantity=" + quantity + ", minimum=" + minimum
				+ ", invoice=" + invoice + ", invoiceResp=" + invoiceResp
				+ ", enterDateInvoice=" + enterDateInvoice + ", emissionDate="
				+ emissionDate + ", branch=" + branch + ", unitaryValue="
				+ unitaryValue + ", user=" + user + ", subCategory="
				+ subCategory + ", listLocation=" + listLocation + ", cpa="
				+ cpa + ", printShop=" + printShop + "]";
	}

	
}
