package companyManager;

/*
 * Copyright (c) 2016 Mateusz Chiliñski https://chilinski.eu. All rights reserved.
 * 
 */

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.swing.*;

import company.*;
import fileSystem.*;

public class CompanyManager {
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			String url = new File("").getAbsolutePath()+"\\testingCompany.txt";
			FileSystem myFile = new FileSystem();
			File f = new File(url);
			if(f.exists() && !f.isDirectory()) {
				try {
					String myCompanyString = myFile.loadFile(url);
					Company modelCompany = myFile.importCompany(myCompanyString);
					CompanyView viewCompany = new CompanyView();
					CompanyController myCompanyController = new CompanyController(modelCompany, viewCompany);
					myCompanyController.printLocations();
				} 
				catch (IOException e) {
					e.printStackTrace();
					System.err.println(e.getMessage());
				}
			}
			else
			{
				Company modelCompany = new Company();
				CompanyView viewCompany = new CompanyView();
				CompanyController myCompanyController = new CompanyController(modelCompany, viewCompany);
				myCompanyController.printLocations();
			}
		});
	}

}