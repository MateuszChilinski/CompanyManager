package companyManager;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class CompanyController {
	private Company model;
	private CompanyView view;
	CompanyController(Company model, CompanyView view)
	{
		this.model = model;
		this.view = view;
	}
	public void printLocations()
	{
		view.printLocations(model.getLocations());
	}
}
