package companyManager;


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import company.*;

public class CompanyManager 
{
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(() -> {
			Company modelCompany = new Company();
			CompanyView viewCompany = new CompanyView();
			CompanyController myCompanyController = new CompanyController(modelCompany, viewCompany);
			myCompanyController.printLocations();
		});
		//System.exit(0);
	}

}