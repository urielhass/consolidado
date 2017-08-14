package br.com.stock.enums;

public enum UserPermission {
	REQUEST(10), STOCK(20), ADMIN(30);
	
	private final int value;
	
	UserPermission(int value){
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
