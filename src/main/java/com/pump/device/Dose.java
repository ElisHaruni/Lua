package com.pump.device;

/**
 * Represent an hormone injection, either insulin or glucagon.
 * 
 * @author Raul Bertone
 */
public class Dose {

	private Substances sub;
	private int amount;
	private long timeStamp = System.currentTimeMillis();
	
	public Dose(Substances sub, int amount){
		this.sub = sub;
		this.amount = amount;
	}
	
	public Substances getSub(){
		return sub;
	}
	
	public int getAmount(){
		return amount;
	}
	
	public long getTimeStamp(){
		return timeStamp;
	}
}
