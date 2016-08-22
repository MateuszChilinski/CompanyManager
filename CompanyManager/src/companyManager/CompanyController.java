package companyManager;

import java.util.ArrayList;

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
