package com.pump.helpers;

import java.math.*;

import com.pump.device.ArtificialPancreas;
import com.pump.models.BloodModel;
import com.pump.models.PatientModel;
public class Simulation {
	
	public static Mode mode;
	public static Boolean isRuning;
	private static PatientModel patient = PatientModel.getPatientModel();
	static  BloodMonitor monitor ;
	public static double time ;
	static 	ArtificialPancreas device;
	
	
	public Simulation(Mode mode)
	{
		monitor = new BloodMonitor();
		device = new ArtificialPancreas();
		//hormoneEngine = new HormoneSystem();
		//engine = new BloodSugarEngine();
		this.mode=mode;
		
		
	}
	public void startSimulation()
	{
		//TODO: Here we should start the graph, displaying data for blood Sugar we get data from BloodModel S
		
		isRuning=true;
		
	    device.setName("Pump");
	    device.start();
	    monitor.setName("BloodMonitor");
	    monitor.start();
	}
	public static void stopSimulation()
	{
		for (Thread t : Thread.getAllStackTraces().keySet()) {
	        if (t.getName().equals("DiabeticEngine")||t.getName().equals("InsulineEngine"))
	        {
	        	if (t.isAlive())
	        	{		        	
	        		t.stop();
	        	}
	        }
	      
	    }
		
		monitor.stop();
		BloodModel model = BloodModel.getInstance();
		patient.resetPatientData();
		model.resetBloodSugar();
		isRuning=false;
	}
	public static void InsertCBH(BigDecimal amountOFSugar)
	{
		
		SugarEngine sen=new SugarEngine(amountOFSugar);
		sen.setName("DiabeticEngine");
    	sen.start();
    	/*
		engine.setNewAmountOfSugar(amountOFSugar);
		if(!engine.isAlive())
		{
			engine.setName("DiabeticEngine");
			engine.start();
		}
		
	    //if(BloodSugarEngine.currentThread().is)
		*/
	}


}
