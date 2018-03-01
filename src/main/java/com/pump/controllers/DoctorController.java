/**
 * Sample Skeleton for 'DoctorView.fxml' Controller Class
 */

package com.pump.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.pump.helpers.ControlType;
import com.pump.helpers.UserType;
import com.pump.models.PatientModel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DoctorController implements Initializable {

	PatientModel patient = PatientModel.getPatientModel();
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="lblMaxInsulin"
    private Label lblMaxInsulin; // Value injected by FXMLLoader

    @FXML // fx:id="txtEmergency"
    private TextArea txtEmergency; // Value injected by FXMLLoader

    @FXML // fx:id="txtLastname"
    private TextArea txtLastname; // Value injected by FXMLLoader

    @FXML // fx:id="txtHeight"
    private TextArea txtWeight; // Value injected by FXMLLoader

    @FXML // fx:id="txtMaxInsulin"
    private TextArea txtMaxInsulin; // Value injected by FXMLLoader

    @FXML // fx:id="lblAge"
    private Label lblAge; // Value injected by FXMLLoader

    @FXML // fx:id="lblInsulinRate"
    private Label lblInsulinRate; // Value injected by FXMLLoader

    @FXML // fx:id="btnCancle"
    private Button btnCancle; // Value injected by FXMLLoader

    @FXML // fx:id="lblDigetionRate"
    private Label lblDigetionRate; // Value injected by FXMLLoader

    @FXML // fx:id="txtInsulin"
    private TextArea txtInsulin; // Value injected by FXMLLoader

    @FXML // fx:id="lblModeSelection"
    private Label lblModeSelection; // Value injected by FXMLLoader

    @FXML // fx:id="chkManualOnly"
    private CheckBox chkManualOnly; // Value injected by FXMLLoader

    @FXML // fx:id="lblFirstName"
    private Label lblFirstName; // Value injected by FXMLLoader

    @FXML // fx:id="lblBGL"
    private Label lblBGL; // Value injected by FXMLLoader

    @FXML // fx:id="txtSex"
    private TextArea txtSex; // Value injected by FXMLLoader

    @FXML // fx:id="lblLastname"
    private Label lblLastname; // Value injected by FXMLLoader

    @FXML // fx:id="txtDigestionRate"
    private TextArea txtDigestionRate; // Value injected by FXMLLoader

    @FXML // fx:id="txtMaxGlucagon"
    private TextArea txtMaxGlucagon; // Value injected by FXMLLoader

    @FXML // fx:id="btnHist"
    private Button btnHist; // Value injected by FXMLLoader

    @FXML // fx:id="txtFirstName"
    private TextArea txtFirstName; // Value injected by FXMLLoader

    @FXML // fx:id="lblGlucagon"
    private Label lblGlucagon; // Value injected by FXMLLoader

    @FXML // fx:id="lblMaxGlucagon"
    private Label lblMaxGlucagon; // Value injected by FXMLLoader

    @FXML // fx:id="txtPassword"
    private PasswordField txtPassword; // Value injected by FXMLLoader

    @FXML // fx:id="btnLogout"
    private Button btnLogout; // Value injected by FXMLLoader

    @FXML // fx:id="txtGlucagon"
    private TextArea txtGlucagon; // Value injected by FXMLLoader

    @FXML // fx:id="lblPassword"
    private Label lblPassword; // Value injected by FXMLLoader

    @FXML // fx:id="txtAge"
    private TextArea txtAge; // Value injected by FXMLLoader

    @FXML // fx:id="lblHeight"
    private Label lblHeight; // Value injected by FXMLLoader

    @FXML // fx:id="txtMedicine"
    private TextArea txtMedicine; // Value injected by FXMLLoader

    @FXML // fx:id="lblSex"
    private Label lblSex; // Value injected by FXMLLoader

    @FXML // fx:id="btnSave"
    private Button btnSave; // Value injected by FXMLLoader

    @FXML // fx:id="txtBGL"
    private TextArea txtBGL; // Value injected by FXMLLoader

    @FXML // fx:id="lblmedicine"
    private Label lblmedicine; // Value injected by FXMLLoader

    @FXML // fx:id="chkAutoOnly"
    private CheckBox chkAutoOnly; // Value injected by FXMLLoader

    @FXML // fx:id="chkAutoManual"
    private CheckBox chkAutoManual; // Value injected by FXMLLoader

    @FXML // fx:id="lblEmergency"
    private Label lblEmergency; // Value injected by FXMLLoader

    @FXML
    void Text(ActionEvent event) {

    }

    @FXML
    void handleAutoOnly(ActionEvent event) {
    	if (chkAutoOnly.isSelected()) {
    		chkManualOnly.setSelected(false);
    		chkAutoManual.setSelected(false);
    	}
    }

    @FXML
    void handleManualOnly(ActionEvent event) {
    	if (chkManualOnly.isSelected()) {
    		chkAutoOnly.setSelected(false);
    		chkAutoManual.setSelected(false);
    	}
    }

    @FXML
    void handlechkAutoManual(ActionEvent event) {
    	if (chkAutoManual.isSelected()) {
    		chkAutoOnly.setSelected(false);
    		chkManualOnly.setSelected(false);	
    	}
    }

    @FXML
    void logout(ActionEvent event) {

    	patient.setType(UserType.PATIENT);
    	if(this.chkAutoManual.isSelected()&& patient.getControlMode()!=ControlType.FULLCONTROL)
    	{
    		patient.setControlMode(ControlType.FULLCONTROL);
    		
    	}
    	if(this.chkAutoOnly.isSelected())
    	{
    		patient.setControlMode(ControlType.AUTOMATIC);
    		
    	}
    	if(this.chkManualOnly.isSelected())
    	{
    		
    		patient.setControlMode(ControlType.MANUAL);
    	}
    	try {
			Node node = (Node)(event.getSource());
    		node.getScene().getWindow().hide();
    		URL viewUrl = getClass().getResource("/com/pump/views/PatientView.fxml");
			AnchorPane root = (AnchorPane)FXMLLoader.load(viewUrl);
			Stage newStage = new Stage();
			Scene scene = new Scene(root);
			newStage.setScene(scene);
			newStage.setTitle("Configuration Panel");
			newStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	Node node = (Node)event.getSource();
    	node.getScene().getWindow().hide();
    }

    @FXML
    void save(ActionEvent event) {

    	patient.setType(UserType.DOCTOR);
    	if(!this.txtFirstName.getText().equals(patient.getName()))
    	{
    		patient.setName(this.txtFirstName.getText());
    	}
    	if (!this.txtLastname.getText().equals(patient.getSurname()))
    	{
    		patient.setSurname(this.txtLastname.getText());
    	}
    	
    	if(this.chkAutoManual.isSelected()&& patient.getControlMode()!=ControlType.FULLCONTROL)
    	{
    		patient.setControlMode(ControlType.FULLCONTROL);
    		
    	}
    	if(this.chkAutoOnly.isSelected())
    	{
    		patient.setControlMode(ControlType.AUTOMATIC);
    		
    	}
    	if(this.chkManualOnly.isSelected())
    	{
    		
    		patient.setControlMode(ControlType.MANUAL);
    	}
    	try {
			Node node = (Node)(event.getSource());
    		node.getScene().getWindow().hide();
    		URL viewUrl = getClass().getResource("/com/pump/views/PatientView.fxml");
			AnchorPane root = (AnchorPane)FXMLLoader.load(viewUrl);
			Stage newStage = new Stage();
			Scene scene = new Scene(root);
			newStage.setScene(scene);
			newStage.setTitle("Configuration Panel");
			newStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	//Node node = (Node)event.getSource();
    	//node.getScene().getWindow().hide();
    }

    @FXML
    void cancle(ActionEvent event) {

    }

    @FXML
    void getHistory(ActionEvent event) {

    }

    

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		this.txtFirstName.setText(patient.getName());
		this.txtSex.setText(patient.getSex());
		this.txtLastname.setText(patient.getSurname());
		this.txtWeight.setText(String.valueOf(patient.getWeight()));
		this.txtDigestionRate.setText(String.valueOf(patient.getDigestionRate()));
		this.txtInsulin.setText(String.valueOf(patient.getInsIjectRate()));
		this.txtAge.setText(String.valueOf(patient.getAge()));
		this.txtEmergency.setText(patient.getEmail());
		this.txtBGL.setText(String.valueOf(patient.getRecBGLlvl()));
		this.txtMaxInsulin.setText(String.valueOf(patient.getMaxInsulinD()));
		this.txtMaxGlucagon.setText(String.valueOf(patient.getMaxGlucagonD()));
		this.txtPassword.setText(patient.getPassword());
		this.txtMedicine.setText(String.valueOf(patient.getBlousDose()));
		
		if(patient.getControlMode()==ControlType.AUTOMATIC)
		{
			this.chkAutoOnly.setSelected(true);
		}else if(patient.getControlMode()==ControlType.MANUAL)
		{
			this.chkManualOnly.setSelected(true);
		}
		else if(patient.getControlMode()==ControlType.FULLCONTROL)
		{
			this.chkAutoManual.setSelected(true);
			
		}
		
		
		
	}
}
