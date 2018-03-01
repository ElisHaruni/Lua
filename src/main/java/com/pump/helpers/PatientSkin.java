package com.pump.helpers;

import java.math.BigDecimal;

import com.pump.device.Dose;
import com.pump.device.Substances;

/**
 * This interface represents the patient's "skin": through it, the blood sugar level can be checked, and {@link Substances} can be injected.
 * 
 * @author Raul Bertone
 */

public interface PatientSkin {

	/**
	 * Returns a value representing the amount of sugar currently in the patient's blood.
	 *
	 * @return The current value of the patient's blood sugar level
	 */
	BigDecimal getBloodSugarLevel();
	
	/**
	 * Injects the provided amount of substance into the patient.
	 * 
	 * @param dose The dose to be injected
	 */
	void inject(Dose dose);
	
}
