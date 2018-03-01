package com.pump.controllers;

import com.pump.models.*;
import com.pump.device.Dose;
import com.pump.device.SendMail;
import com.pump.device.Substances;
import com.pump.helpers.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PatientController implements Initializable {
 
	PatientModel patient =  PatientModel.getPatientModel();
	BloodModel blood = BloodModel.getInstance();
	int xSeriesData=0;
	 private XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
	 
	 NumberAxis xAxis = new NumberAxis(0, 100, 10);
     NumberAxis yAxis = new NumberAxis(40,200,20);
     
	private StringProperty sug =new SimpleStringProperty("0");
	private StringProperty  sugB= new SimpleStringProperty("0");
	private final StringProperty  date= new SimpleStringProperty("0");
	private StringProperty  alarms= new SimpleStringProperty("ALRAMS!!!");
	private StringProperty  injectedInsulin= new SimpleStringProperty("0");
	private StringProperty  injectedGlucagon= new SimpleStringProperty("0");
	
	private String alm;
	
  
   @FXML
   private AnchorPane graphAnchorPane;

    @FXML
    private Button btnSOS;
    @FXML
    private Label lbltime;
	    
    @FXML
    private Label lbldate;
    @FXML
    private Font x11;
    @FXML
    private Label lblGlucagonCount;
    @FXML
    private Font x2111;

    @FXML
    private Label lblInsulinCount;

    @FXML
    private Font x211;

    @FXML
    private Font x111;
    @FXML
    private Font x21;
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnInjectInsuline"
    private Button btnInjectInsuline; // Value injected by FXMLLoader

    @FXML // fx:id="btnDocMode"
    private Button btnDocMode; // Value injected by FXMLLoader

    @FXML // fx:id="btnInjectGlucagon"
    private Button btnInjectGlucagon; // Value injected by FXMLLoader

    @FXML // fx:id="lblSugarNow"
    private  Label lblSugarNow; // Value injected by FXMLLoader

    @FXML // fx:id="btnAutomaticMode"
    private Button btnAutomaticMode; // Value injected by FXMLLoader

    @FXML // fx:id="btnStartSimulation"
    private Button btnStartSimulation; // Value injected by FXMLLoader

    @FXML // fx:id="lblSugarBefore"
    private   Label lblSugarBefore; // Value injected by FXMLLoader

    @FXML // fx:id="btnMaualMode"
    private Button btnMaualMode; // Value injected by FXMLLoader
    
    @FXML // fx:id="btnEat50Cbh"
    private Button btnEat50Cbh; // Value injected by FXMLLoader
    
    @FXML // fx:id="btnStopSimulation"
    private Button btnStopSimulation; // Value injected by FXMLLoader
    @FXML
    private ProgressBar progGlucagonRes;
    @FXML
    private ProgressBar progInsulineRes;
    
    @FXML
    private Button btnDosport;

    @FXML
    private Button btnFillGliuacgonReservoir;
    @FXML
    private Button btnFillInsulineReservoir;
    
    @FXML
    private Button btnhistory;

    @FXML
    private Label txtAlarms;



    @FXML
    void doingSports(ActionEvent event) {
    	if(Simulation.isRuning)
    	{
    		Simulation.InsertCBH(BigDecimal.valueOf(-50.0d));
    	}
    }
    @FXML
    void fillInsulineReservoir(ActionEvent event) {
        this.progInsulineRes.setProgress(1);
    }

    @FXML
    void fillGliuacgonReservoir(ActionEvent event) {
    	this.progGlucagonRes.setProgress(1);
    }
    @FXML
    public void injectInsuline(ActionEvent event) {
     BloodModel blood = BloodModel.getInstance();
     
     blood.inject(new Dose(Substances.INSULIN , 4));
    	
     
    }
    @FXML
    public void injectGlucagon(ActionEvent event) {
    	  BloodModel blood = BloodModel.getInstance();
    	     blood.inject(new Dose(Substances.GLUCAGON , 4));
    }

    @FXML
    public void changeToManualMode(ActionEvent event) {
    	
      	
    }

    @FXML
    public void changeToAutomaticMode(ActionEvent event) {
       
    	
    	
    	
    }

    @FXML
    public void startSimulation(ActionEvent event) {
  
    	Simulation sim = new Simulation(Mode.AUTOMATIC);
    	sim.startSimulation();
    	
    	 prepareTimeline();
    	bindBSLTOlabel();
    }
	    
	    @FXML
	public void login(ActionEvent event) {

	    	try {
	    		
	    		Simulation.stopSimulation();
	    		Node node = (Node)(event.getSource());
        		node.getScene().getWindow().hide();
	    		URL viewUrl = getClass().getResource("/com/pump/views/LoginView.fxml");
				AnchorPane root = (AnchorPane)FXMLLoader.load(viewUrl);
				Stage newStage = new Stage();
				Scene scene = new Scene(root);
				newStage.setScene(scene);
				newStage.setTitle("Login");
				newStage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }

    @FXML	
    public void eat50cbh(ActionEvent event) {

    	
	    	if(Simulation.isRuning)
	    	{
	    		Simulation.InsertCBH(BigDecimal.valueOf(50.0d));
	    	}
	    }
	    @FXML
    public void stopSimulation(ActionEvent event) {
             Simulation.stopSimulation();       
         	
             Task<Void> task = new Task<Void>() {
                 @Override
                 protected Void call() throws Exception {
                 	
                 	while(!Simulation.isRuning)
                 	{
                 		Thread.sleep(10);
                   	  
                  	   Platform.runLater(new Runnable() {

                             @Override
                             public void run() {
                             	sug.setValue(String.valueOf(0));
                             	sugB.setValue(String.valueOf(0));
                             	
                             }
                         });
                  	   
                 	}
                 	return null;
                   
                 }
              };
              Thread th = new Thread(task);
              th.setDaemon(true);
              th.start();
	    }
	    

	    @FXML
	 public   void showHstory(ActionEvent event) {
	    	/*
	    	BloodModel currBlood= BloodModel.getInstance();
	    	 HashMap<Double, BigDecimal> history = currBlood.getHistory();
	    	 
	    	 //final NumberAxis xAxis = new NumberAxis();
	         //final NumberAxis yAxis = new NumberAxis();
	         xAxis.setLabel("Time");
	         yAxis.setLabel("BSL");
	 		bloodGraph = new LineChart<Number,Number>();
	 		//bloodGraph.setTitle("Blood Sugar Level");
	         XYChart.Series series = new XYChart.Series();
	  		
	         for(Double time: history.keySet())
	         {
	        	 series.getData().add(new XYChart.Data(String.valueOf(time), history.get(time)));
	         }
	  		
			 
	        
	        bloodGraph.getData().add(series);
	   	*/
 
	    }
	    
	    @FXML
	  public void sendSOSAlarm(ActionEvent event) {


	    	try {
				SendMail.send("Alarm! SOS button pressed!");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	patient.setAlarms("NEW ALARM!!!!");
	  
	    }
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO progerss must be calculated by our actual value we have in reservoir
		initializeGraph(graphAnchorPane, series1);
		 prepareTimeline();
		//this.progGlucagonRes.progressProperty().bind(gluRes);    
		this.progGlucagonRes.setProgress(0.1);
		this.progInsulineRes.setProgress(0.1);
		this.txtAlarms.textProperty().bind(alarms);
		this.lblSugarNow.textProperty().bind(sug);
		this.lblSugarBefore.textProperty().bind(sugB);
		setDate();
		setTime();
		this.lbldate.textProperty().bind(date);
		this.lblInsulinCount.textProperty().bind(injectedInsulin);
		this.lblGlucagonCount.textProperty().bind(injectedGlucagon);
		
		if(patient.getType()==UserType.CAREGIVER)
		{
			this.btnSOS.setVisible(false);
		}
		else if(patient.getType()==UserType.PATIENT)
		{
			if(patient.getControlMode()==ControlType.AUTOMATIC)
			{
				this.btnAutomaticMode.setDisable(true);
				this.btnMaualMode.setDisable(true);
				this.btnInjectGlucagon.setDisable(true);
				this.btnInjectInsuline.setDisable(true);
			}
			if(patient.getControlMode()==ControlType.MANUAL)
			{
				this.btnAutomaticMode.setDisable(true);
				this.btnMaualMode.setDisable(true);
			}
		}
		else if(patient.getType()==UserType.DOCTOR)
		{
		 	
		}
		
		
		
		
		
	}
	
	
private void initializeGraph(AnchorPane graphAnchorPane, XYChart.Series<Number, Number> bloodSeries) {
	
	xAxis.setLabel("time in minutes");
    yAxis.setLabel("mg/dl");
     
     final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis) {
         // Override to remove symbols on each data point
         @Override
         protected void dataItemAdded(Series<Number, Number> series, int itemIndex, Data<Number, Number> item) {
         }
     };
     lineChart.setAnimated(true);
     lineChart.setTitle("Blood Sugar Level");
     lineChart.setHorizontalGridLinesVisible(true);
     lineChart.setVerticalGridLinesVisible(false);
	
     bloodSeries.setName("BGL");
     lineChart.getData().addAll(bloodSeries);
     
     graphAnchorPane.getChildren().add(lineChart);
}

private void prepareTimeline() {
    // Every frame to take any data from queue and add to chart
    new AnimationTimer() {
        @Override
        public void handle(long now) {
            addDataToSeries();
        }
    }.start();
}
private void addDataToSeries() {
    for (int i = 0; i < 3; i++) { //-- add 20 numbers to the plot+
        if (blood.getBloodQue().isEmpty()) 
        break;
        
        series1.getData().add(new XYChart.Data<>(xSeriesData++, blood.removeHead()));    
    }
   
    if (series1.getData().size() > 300) {
        series1.getData().remove(0, series1.getData().size() - 300);
    }
    
    // update
    if(xSeriesData>100)
    {
    	xAxis.setLowerBound(xSeriesData-100);
    }
    else
    {
    	xAxis.setLowerBound(0);
    }
   
    xAxis.setUpperBound(xSeriesData - 1);
}
private void setDate() {
		 
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate localDate = LocalDate.now();
		date.setValue(dtf.format(localDate));
	
	}
	
private void setTime()
{

	Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {            
        Calendar cal = Calendar.getInstance();
        //second = cal.get(Calendar.SECOND);
        //minute = cal.get(Calendar.MINUTE);
        //hour = cal.get(Calendar.HOUR);
        //System.out.println(hour + ":" + (minute) + ":" + second);
        lbltime.setText(cal.get(Calendar.HOUR) + ":" + (cal.get(Calendar.MINUTE)) + ":" + cal.get(Calendar.SECOND));
    }),
         new KeyFrame(Duration.seconds(1))
    );
    clock.setCycleCount(Animation.INDEFINITE);
    clock.play();
	}


private void bindBSLTOlabel() {

	Task<Void> task = new Task<Void>() {
        @Override
        protected Void call() throws Exception {
        	
        	while(Simulation.isRuning)
        	{
        		Thread.sleep(10);
          	  
         	   Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                    	sug.setValue(String.valueOf(patient.getSugarNow()));
                    	sugB.setValue(String.valueOf(patient.getSougarBefore()));
                    	alarms.setValue(patient.getAlarms());
                    	injectedInsulin.setValue(String.valueOf(500-blood.getInsAmount()));
                    	injectedGlucagon.setValue(String.valueOf(500-blood.getGluAmount()));
                    }
                });
         	   
        	}
        	return null;
          
        }
     };
     Thread th = new Thread(task);
     th.setDaemon(true);
     th.start();
     
}
}
