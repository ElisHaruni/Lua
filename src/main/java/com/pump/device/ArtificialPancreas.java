package com.pump.device;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.pump.models.BloodModel;

/**
 * This class is the centerpiece of the artificial pancreas simulator.
 * It monitors the blood sugar level through a {@link BloodSugarLevelSensor}. Based on the blood sugar level value, its recent history and the parameters
 * set by the physician, it calculates the amount of insulin and glucagon to inject.
 * It raises alarms if any abnormal situation arises.
 * 
 * @author Raul Bertone
 */

public class ArtificialPancreas extends Thread{

	// TODO move following data to file
	private int totalDailyDose = 40; // expressed in insulin units
	private double basalRatio = 0.4; // expressed as a portion of the totalDailyDose
	private int basalFrequency = (int) (288 / (totalDailyDose * basalRatio)); // 288 5 minutes intervals in a day, divide by the number of basal insulin units
	private int basalDoseInterval = 0;
	private int glucagonInterval = 0;
	private int remainingDailyUnits = totalDailyDose;
	private List<Dose> activeDoses = new LinkedList<Dose>();
	private int previousBSL = 0;
	private int prePreviousBSL = 0;
	
	private Reservoir fullGlucCardridge = new GlucagonReservoir(500); // TODO, when we move this value to file, save the current value, not always 500
	private Reservoir fullInsuCardridge = new InsulinReservoir(500); // TODO, when we move this value to file, save the current value, not always 500
	private boolean manualMode = false; // true if manual mode is active, false for automatic mode
	private boolean warning = false;
	BloodModel instance = BloodModel.getInstance();
	
	private Pump gluPump = new GlucagonPump(fullInsuCardridge);
	private Pump insPump = new InsulinePump(fullGlucCardridge);
	private Sensor BSLSensor = new BloodSugarLevelSensor();
	private boolean terminate = false; // when set true the device shuts down and the thread stops	
	
	public void run() {
		
		while(!terminate){
			
			if (warning) {
				sendAlarm("Alarm! Patient Unresponsive.");
			}
			
			if(!manualMode){ // only perform automatic injections if manual mode is not active
				
				/*
				 * synchronized to prevent user activity (e.g. manual mode activation, reservoir swap, etc.) 
				 * from interfering with the calculation and subsequent injection.
				 */
				synchronized (this) {
					Dose dose = calculateDose();
					
					if(dose != null){
						switch (dose.getSub()){
							case INSULIN: 
								if (dose.getAmount()<=insPump.getReservoirLevel()){
									inject(dose);
								} else {
									// TODO raise warning
								}
								break;
							case GLUCAGON:
								if (dose.getAmount()<=gluPump.getReservoirLevel()){
									inject(dose);
								} else {
									// TODO raise warning
								}
								break;
						}
						instance.setInsAmount(insPump.getReservoirLevel());
						instance.setGluAmount(gluPump.getReservoirLevel());
					}
				}
			}
			
			try
			{
				Thread.sleep(1000);
			}
			catch(InterruptedException ex)
			{
				System.out.println("Mist!");
			}
		}
		
	}
	
	/**
	 * Removes one of the current reservoirs and inserts a different one.
	 * 
	 * @param sub glucagon or insulin
	 * @param units How much liquid the reservoir contains, range 0-100 
	 */
	public void exchangeReservoir(Substances sub, int units){
		// synchronized to prevent interference with ongoing automatic calculations and injections
		synchronized (this) {
			switch (sub) {
			case GLUCAGON:
				gluPump.exchangeReservoir(new GlucagonReservoir(units));
				break;
			case INSULIN:
				insPump.exchangeReservoir(new InsulinReservoir(units));
				break;
			}
		}
	}
	
	/*
	 * Calculates the amount of insulin or glucagon to be injected based on the current BSL, its recent history and the 
	 * parameters set by the doctor.
	 * 
	 * @return the amount to inject. Positive values represent insulin, negative ones glucagon. Zero means no injection necessary
	 */
	private Dose calculateDose(){
		
		/*
			1 unit will drop 50mg/l
			total daily is 0.55 x kg
			basal is 50% of total daily
		The algorithm is as follows:
		+	When the BGL is within the interval of 80-130 ml/dl, only basal dose in injected
		+	When the BGL rises above 130 ml/dl, insulin is injected; a warning is sent to the patient;
		+	When the BGL sinks below 80 ml/dl, glucagon is injected; a warning is sent to the patient;
		+	If at anytime the patient doesn´t react to a warning within 3 minutes, a remote alarm is sent;
		+	Previous insulin doses are considered to be effective between 1 and 4 hours after they are made;
			If, while under the effect of previous doses the BGL rise doesn´t slow down, a new injection is made;
		+	If the BGL rises over 200 ml/dl, insulin is injected regardless of current effective doses; a warning and an alarm are sent;
		+	If the BGL sinks below 70 ml/dl, 1 Unit glucagon is injected regardless of current effective doses; a warning and an alarm are sent;
		+	The maximum daily dosages defined by the doctor are never surpassed; if they are reached a warning is sent to the patient.
		*/
		
		Dose dose; // to be returned
		
		Iterator<Dose> iter = activeDoses.listIterator();
		
		while (iter.hasNext()) {
			if (System.currentTimeMillis() - 120000 /*4 hours*/ > iter.next().getTimeStamp()) {
				iter.remove();;
			}
		}
		
		int currentBSL = BSLSensor.getValue().intValue();
		int diff1 = prePreviousBSL - previousBSL;
		int diff2 = previousBSL - currentBSL;
		boolean slows = false;
		
		if(diff2>diff1 || diff2>=0) slows = true;
		
		
		// values in nominal range
		if (80 <= currentBSL && currentBSL <= 130) {
			/*if (basalDoseInterval < basalFrequency) {
				basalDoseInterval++; // too early for basal dose
				return null;
			} else {
				
				if (remainingDailyUnits == 0) {
					// TODO send warning
					return null;
				}
				
				remainingDailyUnits--;
				dose = new Dose(Substances.INSULIN, 1); // basal dose
				basalDoseInterval = 0;
				activeDoses.add(dose);
				return dose;
			}*/
		} else if (currentBSL > 200) {
			sendAlarm("Alarm! Hyperglycemia.");
			// TODO send warning
			
			if (remainingDailyUnits == 0) {
				// TODO send warning
				return null;
			}
			
			remainingDailyUnits--;
			dose = new Dose(Substances.INSULIN, 1);
			activeDoses.add(dose);
			return dose;
		} else if (currentBSL > 130) {
			// TODO send warning
			
			if (remainingDailyUnits == 0) {
				// TODO send warning
				return null;
			}
			
			// if there are active doses and the BSL slows
			if(!activeDoses.isEmpty() && slows) return null;
			
			int maxActiveDoses = 1+(currentBSL-130)/25;
			
			int activeAmount = 0;
			for (Dose d: activeDoses) {
				activeAmount = activeAmount + d.getAmount();
			}
			
			int amount = maxActiveDoses - activeAmount;
			if (amount > 0){
				remainingDailyUnits = remainingDailyUnits - amount;
				dose = new Dose(Substances.INSULIN, amount);
				activeDoses.add(dose);
				return dose;
			}
			
			return null;
			
		} else if (currentBSL < 70) {
			sendAlarm("Alarm! Hypoglycemia.");
			glucagonInterval = 0;
			return new Dose(Substances.GLUCAGON, 1);
		} else if (currentBSL < 80) {
			if (glucagonInterval >= 15) {
				glucagonInterval = 0;
				return new Dose(Substances.GLUCAGON, 1);
				// TODO send warning
			} else {
				glucagonInterval = glucagonInterval +5;
			}
		}
		
		return null;
	}
	
	private void sendAlarm(String message) {
		try {
			SendMail.send(message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Invoke to turn the device off. Persistent information will be saved and the thread will be stopped.
	 */
	public void terminate(){
		terminate = true;
	}
	
	/**
	 * Toggles between manual and automatic mode.
	 * 
	 * @param activate True activates manual mode, false deactivates it
	 */
	public void manualMode(boolean activate){
		// synchronized to prevent interference with ongoing automatic calculations and injections
		synchronized (this) {
			manualMode = activate;
		}
	}

	/**
	 * Manually inject insulin or glucagon.
	 * Only possible in manual mode.
	 * 
	 * @param sub The substance to inject.
	 */
	public void manualInject(Dose dose){
		if(manualMode){
			// TODO alert if not enough substance left in reservoir
			inject(dose);
		}
	}
	
	/*
	 * Blindly tries to inject the requested amount. Checks for reservoir status and safety must be done upstream.
	 */
	private void inject(Dose dose){
		switch (dose.getSub()) {
		case GLUCAGON: 
			gluPump.pump(dose);
			break;		
		case INSULIN:
			insPump.pump(dose);
			break;
		}
	}
	
	public int reservoirStatus(Substances sub){
		switch (sub) {
		case GLUCAGON: return gluPump.getReservoirLevel();
		case INSULIN: return insPump.getReservoirLevel();
		default: return -1;
		}
	
	}
	
	/**
	 * Use this method to fetch outstanding warning messages.
	 * The oldest outstanding message is returned.
	 * If no message is present the empty String is returned.
	 * 
	 * @return A warning message
	 */
	public String fetchMessage(){
		return "";
	}
}
