package company;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import company.Company;
import company.CompanyView;
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
	public void setName(String name)
	{
		model.setName(name);
	}
	public String getName() {
		return model.getName();
	}
	public void locationEditor(JFrame owner, Location location)
	{
		LocationView locationView = new LocationView();
		LocationController locationController = new LocationController(location, locationView);
		locationController.locationEditor(owner);
	}
}
