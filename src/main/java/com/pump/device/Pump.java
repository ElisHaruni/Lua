package com.pump.device;

/**
 * This interface allows to control a pump.
 * 
 * @author Raul Bertone
 */

public interface Pump {
	
	/**
	 * Instructs the pump to pump amount of liquid
	 * 
	 * @param Dose the dose to inject
	 */
	void pump(Dose dose);
	
	/**
	 * Disconnects the pump from the current reservoir and connects it to a new one.
	 * 
	 * @param res
	 */
	void exchangeReservoir(Reservoir res);
	
	/**
	 * As a pump pushes liquid out of the reservoir it is connected to, its actuator advances.
	 * Knowing the actuator's position is equivalent to knowing how much liquid is left in the reservoir.
	 * 
	 * @return
	 */
	public int getReservoirLevel();
}