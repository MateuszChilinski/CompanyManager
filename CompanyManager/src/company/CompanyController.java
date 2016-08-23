package company;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import company.Company;
import company.CompanyView;

public class CompanyController {
	private Company model;
	private CompanyView view;
	public CompanyController(Company modelCompany, CompanyView view)
	{
		//model.setController(this);
		view.setController(this);
		this.model = modelCompany;
		this.view = view;
	}
	public void printLocations()
	{
		view.printLocations(model.getLocations());
	}
	public void addLocation(String name)
	{
		model.addLocation(name);
		view.addLocation(name);
		//TODO: add location to file/server as well
	}
}
