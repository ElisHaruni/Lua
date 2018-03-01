package com.pump.device;

import java.math.BigDecimal;

/**
 * This class represents a pump for glucagon.
 * 
 * @author Raul Bertone
 */
public class GlucagonPump extends AbstractPump {

	public GlucagonPump(Reservoir res) {
		super(res);
	}

	@Override
	public void pump(Dose dose) {
		int amount = res.getSubstance(dose.getAmount());
		/*
		 * creates a new Dose, with the amount of substance recovered from the reservoir.
		 * This is to simulate the fact that the reservoir is dumb, and it might serve a lower amount of liquid if it's empty.
		 * Safety checks must be done upstream.
		 */
		instance.inject(new Dose(Substances.GLUCAGON, amount));
	}

}
