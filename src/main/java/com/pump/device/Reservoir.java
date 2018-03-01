package com.pump.device;

/**
 * This interface gives access a reservoir of liquid.
 * 
 * @author Raul Bertone
 */

public interface Reservoir {
	
	/**
	 * Through this method a pump can get the liquid it needs to inject.
	 * 
	 * @param amount The quantity of liquid to be dispensed by the reservoir.
	 * @return The quantity of liquid actually dispensed by the reservoir.
	 */
	int getSubstance(int amount);
	
	/**
	 * To check how much liquid is left in the reservoir.
	 * 
	 * @return the amount of liquid left
	 */
	int getFillLevel();
}
