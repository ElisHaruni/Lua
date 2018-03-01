package com.pump.device;

import com.pump.models.BloodModel;

/**
 * A generic pump.
 * 
 * @author Raul Bertone
 */
public abstract class AbstractPump implements Pump{

	protected Reservoir res; // the reservoir this pump is connected to
	protected BloodModel instance = BloodModel.getInstance(); // the patient's blood. The pump pumps liquid into it.
	
	public AbstractPump(Reservoir res){
		this.res = res;
	}
	
	@Override
	public void exchangeReservoir(Reservoir res) {
		this.res = res;
	}
	
	@Override
	public int getReservoirLevel(){
		return res.getFillLevel();
	}
}
