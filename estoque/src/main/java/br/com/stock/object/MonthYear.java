package br.com.stock.object;

import java.math.BigInteger;


public class MonthYear {

	private int id;
	private String year;
	private String month;



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getYear() {
		return year;
	}



	public void setYear(String year) {
		this.year = year;
	}



	public String getMonth() {
		return month;
	}



	public void setMonth(String month) {
		this.month = month;
	}



	@Override
	public String toString() {
		return "Location [id=" + id + ", year=" + year
				+ ", month=" + month + "]";
	}
}
