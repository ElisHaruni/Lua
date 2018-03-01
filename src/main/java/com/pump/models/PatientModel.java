package com.pump.models;

import com.pump.helpers.ControlType;
import com.pump.helpers.Mode;
import com.pump.helpers.UserType;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PatientModel {

	
	public static PatientModel patient;
	private double SugarNow=100.00d;
	private double SugarBefore=100.00d;
	private int age=45;
	private String name="Elis";
	private String surname="haruni";
	private String sex="Male";
	private double weight= 80;
	private double digestionRate= 2;
	private double insIjectRate = 4;
	private double gluInjectRate = 3;
	private String email = "hrauni@stud.fra-uas.de";
	private double recBGLlvl = 100d;
	private double maxInsulinD = 55;
	private double maxGlucagonD = 55;
	private double blousDose=4;
	private String password="1234";
	private UserType type = UserType.DOCTOR;
	private ControlType controlMode = ControlType.AUTOMATIC;
	private String alarms="NO ALARMS!!";
	
	
	private PatientModel() {
		
	}
	
	public static PatientModel getPatientModel() {
		
		if(patient==null)
		{
			synchronized(PatientModel.class)
			{
				if(patient==null)
				{
					patient=new PatientModel();
				}
			}
		}
		return patient;
	}
	
	
	public void setSugarNow(double sug)
	{
		patient.SugarNow=sug;	
		String s=String.valueOf(SugarNow);
		
	}
	public double getSugarNow() {
		return patient.SugarNow;
		
	}
	public void setSugarBefore(double sug)
	{
		patient.SugarBefore=sug;
		String s=String.valueOf(SugarBefore);
		
	}
	public double getSougarBefore()
	{
		return patient.SugarBefore;
	}
	public void resetPatientData()
	{
		patient.SugarBefore =100;
		patient.SugarNow =100;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getDigestionRate() {
		return digestionRate;
	}
	public void setDigestionRate(double digestionRate) {
		this.digestionRate = digestionRate;
	}
	public double getInsIjectRate() {
		return insIjectRate;
	}
	public void setInsIjectRate(double insIjectRate) {
		this.insIjectRate = insIjectRate;
	}
	public double getGluInjectRate() {
		return gluInjectRate;
	}
	public void setGluInjectRate(double gluInjectRate) {
		this.gluInjectRate = gluInjectRate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public double getRecBGLlvl() {
		return recBGLlvl;
	}
	public void setRecBGLlvl(double recBGLlvl) {
		this.recBGLlvl = recBGLlvl;
	}
	public double getMaxInsulinD() {
		return maxInsulinD;
	}
	public void setMaxInsulinD(double maxInsulinD) {
		this.maxInsulinD = maxInsulinD;
	}
	public double getMaxGlucagonD() {
		return maxGlucagonD;
	}
	public void setMaxGlucagonD(double maxGlucagonD) {
		this.maxGlucagonD = maxGlucagonD;
	}
	public double getBlousDose() {
		return blousDose;
	}
	public void setBlousDose(double blousDose) {
		this.blousDose = blousDose;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public UserType getType() {
		return type;
	}
	public void setType(UserType type) {
		this.type = type;
	}
	public ControlType getControlMode() {
		return controlMode;
	}
	public void setControlMode(ControlType controlMode) {
		this.controlMode = controlMode;
	}

	public String getAlarms() {
		return alarms;
	}

	public void setAlarms(String alarms) {
		this.alarms = alarms;
	}
	
	
	
}
