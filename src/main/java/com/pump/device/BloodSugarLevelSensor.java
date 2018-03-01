package com.pump.device;

import java.math.BigDecimal;

import com.pump.models.BloodModel;

/**
 * A sensor that measures the blood sugar content.
 * 
 * @author Raul Bertone
 */
public class BloodSugarLevelSensor implements Sensor {
	BloodModel instance = BloodModel.getInstance();
	
	@Override
	public BigDecimal getValue() {
		return instance.getBloodSugarLevel();
	}
}
