package com.pump.helpers;
import java.math.*;
import com.pump.models.*;
import java.io.*;

public class SugarEngine extends Thread {

	private BigDecimal sugarLeft;
	private BigDecimal sugarToAdd ;
	private double startTime;
	
	private BigDecimal initialValue= BigDecimal.valueOf(100.0d);
	private BigDecimal digestCoeficient= BigDecimal.valueOf(0.0453d);
	private BigDecimal insuliCoeficent = BigDecimal.valueOf(0.0224d);
	
	private BloodModel blood = BloodModel.getInstance();
	
	public SugarEngine(BigDecimal CBH)
	{
		this.sugarLeft=CBH;
		this.sugarToAdd=CBH;
		this.startTime=0;
		
	}
	public void run() {
		try {
			//time needed to start digesting and insertion of glucose in blod.
			Thread.sleep(1000);
			startTime=startTime+1;
		}
		catch(InterruptedException ex)
		{
			System.out.println("Somebody waked me up see the log file for more info");
		}		
		while(this.sugarLeft.intValue()!=0)
		{
			BloodSim(this.sugarToAdd, startTime);
			startTime+=1;
			
			try
			{
				// this means that we scale 1 min on 1000ms (1s)because we adding 1 to start time 
				Thread.sleep(1000);
			}
			catch(InterruptedException ex)
			{
				System.out.println("Somebody waked me up see the log file for more info");
			}
			
		}
	}
	
	private void BloodSim(BigDecimal carbohidrates, double time)
	{
		
		
		double timepass=time;
		BigDecimal G0= this.initialValue;
		BigDecimal A0 = carbohidrates;
		BigDecimal k1 = digestCoeficient;
		BigDecimal k2 = BigDecimal.ZERO;
		BigDecimal Gt; 
		BigDecimal A0k1k2 = A0.multiply(k1.divide(k2.subtract(k1),6,RoundingMode.HALF_UP));
	    BigDecimal k1t= k1.multiply(BigDecimal.valueOf(time));
	    BigDecimal k2t = k2.multiply(BigDecimal.valueOf(time));
	    double diffE= Math.exp(-k1t.doubleValue())-Math.exp(-k2t.doubleValue());
	    
	    BigDecimal valueToAdd= A0k1k2.multiply(BigDecimal.valueOf(diffE));
	    Gt=G0.add(valueToAdd);
	    
	    BigDecimal k1tm1= k1.multiply(BigDecimal.valueOf(time-1));
	    BigDecimal k2tm1 = k2.multiply(BigDecimal.valueOf(time-1));
	    double diffEminus1 =Math.exp(-k1tm1.doubleValue())-Math.exp(-k2tm1.doubleValue());
	   
	    BigDecimal Gtminus1 = G0.add(A0k1k2.multiply(BigDecimal.valueOf(diffEminus1)));
   
	    BigDecimal vv= Gt.subtract(Gtminus1);
	    this.sugarLeft=this.sugarLeft.subtract(vv);
	   
	    //System.out.println("Gt: "+Gt+"  Time:"+time);
	   // System.out.println("VV: "+vv+"  Time:"+time);
	   
	    
	    blood.addSugar(vv);
	  //  System.out.println("BSLS: "+blood.getBloodSugarLevel()+"  Time:"+time);
		//blood.setBloodSugar(Gt);
		
	
		
	}
	
}
