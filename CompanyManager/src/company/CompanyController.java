package company;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.swing.*;

import company.Company;
import company.CompanyView;
import companyManager.CompanyManager;
import fileSystem.FileSystem;
import location.Location;
import location.LocationController;
import location.LocationView;

public class CompanyController {
	private Company model;
	private CompanyView view;
	public CompanyController(Company modelCompany, CompanyView view)
	{
		//model.setController(this);
		this.model = modelCompany;
		view.setController(this);
		this.view = view;
	}
	public void printLocations()
	{
		view.printLocations(model.getLocations());
	}
	public ArrayList<Location> getLocations(String name)
	{
		ArrayList<Location> temp = new ArrayList<Location>();
		for(Location currentLocation : model.getLocations())
		{
			if(currentLocation.getName().toLowerCase().contains(name.toLowerCase()))
				temp.add(currentLocation);
		}
		return temp;
	}
	public Location addLocation(String name)
	{
		return model.addLocation(name);
		//TODO: add location to file/server as well
	}
	public Location addLocation(Location location)
	{
		return model.addLocation(location);
		//TODO: add location to file/server as well
	}
	public Location getLocation(int i)
	{
		return model.getLocation(i);
	}
	public void setName(String name)
	{
		model.setName(name);
	}
	public String getName() {
		return model.getName();
	}
	public void locationEditor(JFrame owner, Location location, boolean isNew)
	{
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
	public void saveData() throws UnsupportedEncodingException, FileNotFoundException, IOException
	{
		FileSystem myFile = new FileSystem();
		String url = CompanyManager.class.getResource("testingCompany.txt").getPath();
		url = url.substring(1, url.length());
		myFile.saveFile(url, myFile.exportCompany(this.model));
	}
}
