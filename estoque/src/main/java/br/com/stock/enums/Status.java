package br.com.stock.enums;

public enum Status {
	AWAITING(10), APPROVED(20), DISAPPROVED(30), ONMYWAY(40), AWAITINGCLOSURE(50), ENDING(60);
	
	private final int value;
	
	Status(int value){
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
