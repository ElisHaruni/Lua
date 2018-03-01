package com.pump.helpers;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.pump.models.BloodModel;

public class InsulineEngine extends Thread {

	private BigDecimal sugarLeft;
	private BigDecimal sugarToAdd ;
	private double startTime;
	
	private BigDecimal initialValue= BigDecimal.valueOf(100.0d);
	private BigDecimal digestCoeficient= BigDecimal.valueOf(0.0453d);
	private BigDecimal insuliCoeficent = BigDecimal.valueOf(0.0224d);
	
	private BloodModel blood = BloodModel.getInstance();
	
	public InsulineEngine(BigDecimal CBH, double startTime)
	{
		this.sugarLeft=CBH;
		this.sugarToAdd=CBH;
		this.startTime=startTime;
		
	}
	public void run() {
				
		while(this.sugarLeft.intValue()!=0 && blood.getBloodSugarLevel().intValue()!=100)
		{
			//BloodSim(this.sugarToAdd, startTime);
			if(sugarToAdd.compareTo(BigDecimal.ZERO)>0)
			{
				blood.addSugar(BigDecimal.valueOf(-0.5d));
				sugarLeft.add(BigDecimal.valueOf(-0.5d));
				startTime+=1;
			}
			else if(sugarToAdd.compareTo(BigDecimal.ZERO)<0)
			{
				blood.addSugar(BigDecimal.valueOf(0.5d));
				sugarLeft.add(BigDecimal.valueOf(0.5d));
				startTime+=1;
			}
			
			
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
	
	private void InserInsuline(BigDecimal carbohidrates)
	{
	
		 blood.addSugar(BigDecimal.valueOf(-0.5d));
		 
	}
	private void BloodSim(BigDecimal carbohidrates, double time)
	{
		
		
		double timepass=time;
		BigDecimal G0= this.initialValue;
		BigDecimal A0 = carbohidrates;
		BigDecimal k1 = digestCoeficient;
		BigDecimal k2 = insuliCoeficent;
		BigDecimal Gt; 
		BigDecimal A0k1k2 = A0.multiply(k1.divide(k2.subtract(k1),6,RoundingMode.HALF_UP));
	    BigDecimal k1t= k1.multiply(BigDecimal.valueOf(time));
	    BigDecimal k2t = k2.multiply(BigDecimal.valueOf(time));
	    double diffE= Math.exp(-k1t.doubleValue())-Math.exp(-k2t.doubleValue());
	    
	    BigDecimal valueToAdd= A0k1k2.multiply(BigDecimal.valueOf(diffE));
	    Gt=G0.add(valueToAdd);
	    
	   
	    BigDecimal k1tm1= k1.multiply(BigDecimal.valueOf(time));
	    BigDecimal k2tm1 = BigDecimal.ZERO;
	    BigDecimal A0k1k2D = A0.negate();
	    double diffED =Math.exp(-k1tm1.doubleValue())-Math.exp(-k2tm1.doubleValue());
	   
	    BigDecimal GtD = G0.add(A0k1k2D.multiply(BigDecimal.valueOf(diffED)));
   
	    BigDecimal vv= Gt.subtract(blood.getBloodSugarLevel());
	    this.sugarLeft=this.sugarLeft.subtract(vv);
	  
	    System.out.println("BSL: "+blood.getBloodSugarLevel()+"  Time:"+time);
	   // System.out.println("Gt: "+Gt+"  Time:"+time);
	   //System.out.println("Gtd: "+GtD+"  Time:"+time);
	   
	    
	    blood.addSugar(vv);
	  
		//blood.setBloodSugar(Gt);
		
	
		
	}
	
}
