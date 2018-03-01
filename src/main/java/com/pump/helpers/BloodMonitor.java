package com.pump.helpers;

import com.pump.controllers.*;
import java.util.*;
import java.math.BigDecimal;

import com.pump.models.BloodModel;
import com.pump.models.PatientModel;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

/**
 * This Class will be responsible for binding data real-time on the Monitor(GUI)
 * 
 * 
 * @author Elis Haruni
 */
public class BloodMonitor extends Thread {

	
	
	BloodModel blood= BloodModel.getInstance();
	BigDecimal LastCheckedSugar=blood.getBloodSugarLevel();
	PatientModel patient= PatientModel.getPatientModel();
	
	List<BigDecimal> SugarStory= new ArrayList<BigDecimal>();
	long timestamp;
	//Thread to check the blood history every 3 min 
	@SuppressWarnings("deprecation")
	public void run() {
		
		timestamp=System.currentTimeMillis();
		while (Simulation.isRuning)
		{			
			
			blood.addVaueToQue();
			
			if(blood.getBloodSugarLevel().compareTo(LastCheckedSugar)!=0)
			{
				patient.setSugarNow(blood.getBloodSugarLevel().doubleValue());
				patient.setSugarBefore(LastCheckedSugar.doubleValue());
				
				
				
			}
				//first check if the level is decreasing because is more dangerous
				if(blood.getBloodSugarLevel().compareTo(LastCheckedSugar)<0)
				{
					//TODO: insert glucagon
					LastCheckedSugar = blood.getBloodSugarLevel();
				}
				if(blood.getBloodSugarLevel().compareTo(LastCheckedSugar)!=0)
				{
					
					//insert insuline automaticly
					if(Simulation.mode==Mode.AUTOMATIC)
					{
						LastCheckedSugar = blood.getBloodSugarLevel();
												
					}
					else if (Simulation.mode==Mode.MANUAL)
					{
						//TODO: wait for insert simuline button to inset simuline and run the same process as in automatic
					}
														
					
				}
			
			
			try
			{
				// this means 3 min on simulation time and 3 sec on real time because 
				// we use scale 1 min 1 sec and we are updating blood every min(sec) 
				Thread.sleep(1000);
			}
			catch(InterruptedException ex)
			{
				System.out.println("Somebody waked me up see the log file for more info");
			}
			
		}
		
		
	}

	
}
