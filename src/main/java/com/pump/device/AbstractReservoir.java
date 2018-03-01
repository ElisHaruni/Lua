package com.pump.device;

/**
 * It emulates a physical insulin/glucagon reservoir cartridge.
 * While such a "dumb" cartridge doesn't possess any sensor to check how much liquid is left, the pump it is connected to can
 * sense how deep its piston has to go before it encounters resistance. This is modeled by allowing the pump to query the reservoir
 * for the fillLevel.
 * Note however that this reservoir doesn't make any internal use of this information: if more liquid is requested than is available,
 * the reservoir will dispense (return) as much as possible, until it is empty. No alarm or exception will be raised. The burden
 * of checking falls onto the pump, because it's the only component containing a sensor.
 * 
 * @author Raul Bertone
 */
public abstract class AbstractReservoir implements Reservoir {

	private int fillLevel; // the amount of liquid left in the reservoir
	
	public AbstractReservoir(int fillLevel){
		this.fillLevel = fillLevel; // a less then full reservoir cartridge might be inserted
	}
	
	@Override
	public int getSubstance(int amount) {
		
		if(fillLevel >= amount) { 		// if there's enough liquid left to fulfill the request
			fillLevel = fillLevel - amount;		// decrease the level
			return amount; 				// return the requested amount
		} else { 						// if the reservoir is too empty to fulfill the request
			int available = fillLevel;
			fillLevel = 0; 				// empty the reservoir
			return available; 			// return all the remaining liquid
		}
	}
	
	@Override
	public int getFillLevel(){
		return fillLevel;
	}

}
