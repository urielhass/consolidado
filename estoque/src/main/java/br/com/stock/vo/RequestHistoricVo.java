package br.com.stock.vo;

import java.util.Date;

public class RequestHistoricVo {

	private int branch;
	private String stock;
	private double unitaryValue;
	private Date entryDate;
	private int documentNumber;
	private int quantity;
	private String costCenter;
	private Date emissionDate;
	private String cpa;
	private String printShop;
	private String project;
	
	private Date enterDateInvoice;
	
	public int getBranch() {
		return branch;
	}

	public void setBranch(int branch) {
		this.branch = branch;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public double getUnitaryValue() {
		return unitaryValue;
	}

	public void setUnitaryValue(double unitaryValue) {
		this.unitaryValue = unitaryValue;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public int getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(int documentNumber) {
		this.documentNumber = documentNumber;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	public Date getEmissionDate() {
		return emissionDate;
	}

	public void setEmissionDate(Date emissionDate) {
		this.emissionDate = emissionDate;
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

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public Date getEnterDateInvoice() {
		return enterDateInvoice;
	}

	public void setEnterDateInvoice(Date enterDateInvoice) {
		this.enterDateInvoice = enterDateInvoice;
	}
}
