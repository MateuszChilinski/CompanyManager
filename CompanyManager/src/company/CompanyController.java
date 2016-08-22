package company;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import company.Company;
import company.CompanyView;

public class CompanyController {
	private Company model;
	private CompanyView view;
	public CompanyController(Company model, CompanyView view)
	{
		this.model = model;
		this.view = view;
	}
	public void printLocations()
	{
		view.printLocations(model.getLocations());
	}
}
