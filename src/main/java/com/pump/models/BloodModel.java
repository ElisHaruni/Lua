package com.pump.models;

import java.math.*;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.pump.device.BloodSugarLevelSensor;
import com.pump.device.Dose;
import com.pump.device.Substances;
import com.pump.helpers.*;
/**
 * THis calss will hold the data for  Blood model Sugar level in real time 
 * also will hve the history of blood sugar level changes
 * 
 * 
 * @author Elis Haruni
 */
public class BloodModel implements PatientSkin {
	
	public static BloodModel bloodModel ;
	private static BigDecimal min = BigDecimal.valueOf(0.0d);
	private static BigDecimal max = BigDecimal.valueOf(300.0d);
	
	private static BigDecimal safeMin = BigDecimal.valueOf(70.0d);
	private static BigDecimal safeMax = BigDecimal.valueOf(140.0d);
	
	public  BigDecimal initialValue=BigDecimal.valueOf(100.0d);
	public BigDecimal targetValue =BigDecimal.valueOf(100.0d);
	
	private double time;
	private  BigDecimal Sugar ;
	private int insAmount=500;
	private int gluAmount=500;
	
	private ConcurrentLinkedQueue<Number> bloodQue = new ConcurrentLinkedQueue<>();
	
	private BloodModel() {
		Sugar=initialValue;
		time=0;
		bloodQue.add((Number)initialValue);
		
		
	}

	//get Singleton Instance
		public static BloodModel getInstance()
		{
			if(bloodModel==null)
			{
				synchronized(BloodModel.class)
				{
					if(bloodModel==null)
					{
						bloodModel=new BloodModel();
					}
				}
			}
			return bloodModel;
		}
	
	public void setBloodSugar(BigDecimal bsNow)
	{
		Sugar=bsNow;
		time+=1;
		
		bloodQue.add((Number)bsNow);
	}
	public   void resetBloodSugar() {
        Sugar = initialValue;
        time=0;
       bloodQue.clear();
    }

	@Override
	public  BigDecimal getBloodSugarLevel() {
		
		if(Sugar==null)
		{
			return initialValue;
		}
		return Sugar;
	}

	public void addSugar(BigDecimal valueToadd)
	{
		Sugar=Sugar.add(valueToadd);
		//
	}

	public void addVaueToQue() {
		bloodQue.add((Number)Sugar);
		
	}
public ConcurrentLinkedQueue<Number> getBloodQue()
{
	return bloodQue;
	}
public Number removeHead()
{
	return bloodQue.remove();
	}
	@Override
	public void inject(Dose dose) {
		// TODO here we will start the thread for blood normalization
		int count=0;
		for (Thread t : Thread.getAllStackTraces().keySet()) {
	        if (t.getName().equals("InsulineEngine"))
	        {
	        	if (t.isAlive())
	        	{		        	
	        		count++;
	        	}
	        }
	      
	    }
		
		if(dose.getSub()==Substances.INSULIN && count<5)
		{
			System.out.println("InjectCalled with:" + dose.getSub() );
			InsulineEngine eng= new InsulineEngine(BigDecimal.valueOf(60),23);
			eng.setName("InsulineEngine");
		      eng.start();
		      
			/*
			for (Thread t : Thread.getAllStackTraces().keySet()) {
		        if (t.getName().equals("DiabeticEngine"))
		        {
		        	if (t.isAlive())
		        	{		        	
		        		t.stop();
		        	}
		        }
		      
		    }
			*/
			
		      /*
			  HormoneSystem hormoneEngine = new HormoneSystem();
		        hormoneEngine.setName("hormoneEngine");
		        hormoneEngine.setAmountOfSugar(BigDecimal.valueOf(50));
		        hormoneEngine.start();
			//int i = ThreadGroup.activeCount();
			//Thread.enumerate(ThreadGroup.activeCount());
			 * 
			 */
		}
		else if(dose.getSub()==Substances.GLUCAGON &&count<3)
		{
			InsulineEngine eng= new InsulineEngine(BigDecimal.valueOf(-50),5);
			eng.setName("InsulineEngine");
		      eng.start();
		}
	}

	public int getInsAmount() {
		return insAmount;
	}

	public void setInsAmount(int insAmount) {
		this.insAmount = insAmount;
	}

	public int getGluAmount() {
		return gluAmount;
	}

	public void setGluAmount(int gluAmount) {
		this.gluAmount = gluAmount;
	}
}
