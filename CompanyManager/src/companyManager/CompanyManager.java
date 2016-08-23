package companyManager;


import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;

import javax.swing.*;

import company.*;
import fileSystem.*;

public class CompanyManager 
{
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(() -> {
			FileSystem myFile = new FileSystem();
			try {
				String url = CompanyManager.class.getResource("testingCompany.txt").getPath();
				url = url.substring(1, url.length());
				String myCompanyString = myFile.loadFile(url);
				Company modelCompany = myFile.importCompany(myCompanyString);
				CompanyView viewCompany = new CompanyView();
				CompanyController myCompanyController = new CompanyController(modelCompany, viewCompany);
				myCompanyController.printLocations();
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println(e.getMessage());
			}
			/**
			Company newCompany = new Company();
			System.out.println(myFile.exportCompany(newCompany));**/
		});
		//System.exit(0);
	}

}