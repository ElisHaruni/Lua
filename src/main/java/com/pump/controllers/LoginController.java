package com.pump.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.pump.helpers.Simulation;
import com.pump.helpers.UserType;
import com.pump.models.PatientModel;

import javafx.fxml.Initializable;

public class LoginController implements Initializable {

	PatientModel patient = PatientModel.getPatientModel();
    @FXML
    private TextField txtUsername;

    @FXML
    private Button btnLogin;

    @FXML
    private TextField txtPassword;

    @FXML
    private Label lblmsg;
    
    @FXML
    private void Login(ActionEvent event) {
    	
    	
    	//String n = txtUsername.getText();
    	if(txtUsername.getText().equals("user") || txtPassword.getText().equals("user")  )
    	{
    		patient.setType(UserType.DOCTOR);
    
    		try {
    			Node node = (Node)(event.getSource());
        		node.getScene().getWindow().hide();
	    		URL viewUrl = getClass().getResource("/com/pump/views/DoctorView.fxml");
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
    		
    	}
    	else if(txtUsername.getText().equals("cgiver") || txtPassword.getText().equals("cgiver") ) {
    		
    		Simulation.stopSimulation();
    		patient.setType(UserType.CAREGIVER);
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
    		
    	}
    	else
    	{
    		lblmsg.setText("Please fill the form");
    		lblmsg.setTextFill(Color.RED);
    	}

    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}
