package company;

/*
 * Copyright (c) 2016 Mateusz Chiliñski https://chilinski.eu. All rights reserved.
 * 
 */

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.swing.*;

import company.Company;
import company.CompanyView;
import companyManager.CompanyManager;
import fileSystem.FileSystem;
import item.Item;
import location.Location;
import location.LocationController;
import location.LocationView;

public class CompanyController {
	private Company model;
	private CompanyView view;
	
	public CompanyController(Company aModel, CompanyView aView) {
		model = aModel;
		aView.setController(this);
		view = aView;
	}
	public ArrayList<Location> getLocations() {
		return model.getLocations();
	}
	public ArrayList<Location> getLocations(String name) {
		ArrayList<Location> temp = new ArrayList<Location>();
		removeNulls();
		for(Location currentLocation : model.getLocations())
		{
			if(currentLocation.getName().toLowerCase().contains(name.toLowerCase()))
				temp.add(currentLocation);
		}
		return temp;
	}
	public Location getLocation(int i) {
		return model.getLocation(i);
	}
	public String getName() {
		return model.getName();
	}
	public void setName(String name) {
		model.setName(name);
	}
	public Location addLocation(String name) {
		return model.addLocation(name);
	}
	public Location addLocation(Location location) {
		return model.addLocation(location);
	}
	private void removeNulls() {
		ArrayList<Location> locations = model.getLocations();
		for(int i = 0; i < locations.size(); i++)
		{
			if(locations.get(i).getName() == null)
			{
				locations.remove(i); i--;
			}
		}
	}
	public void printLocations() {
		view.printLocations(model.getLocations());
	}
	public void locationEditor(JFrame owner, Location location, boolean isNew) {
		LocationView locationView = new LocationView();
		LocationController locationController = new LocationController(location, locationView);
		locationController.locationEditor(owner, isNew);
		if(location.getName() != null && isNew == true)
			this.addLocation(location);
		updateLocations();
	}
	public void updateLocations() {
		view.updateLocations(model.getLocations());
	}
	public void saveData() throws UnsupportedEncodingException, FileNotFoundException, IOException {
		FileSystem myFile = new FileSystem();
		String url = new File("").getAbsolutePath()+"\\testingCompany.txt";
		myFile.saveFile(url, myFile.exportCompany(this.model));
	}
}
