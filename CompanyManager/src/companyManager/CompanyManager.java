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
class CompanyFrame extends JFrame
{
	private static final int DEFAULT_WIDTH = 300;
	private static final int DEFAULT_HEIGHT = 200;
	
	public CompanyFrame()
	{
		setTitle("Company Manager");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		/** Constructing main menu **/
		JMenuBar mainMenu = new JMenuBar();
		setJMenuBar(mainMenu);
		/** Constructing company menu **/
		JMenu companyMenu = new JMenu("Company");
		companyMenu.setToolTipText("Managment of the company as the whole.");
		mainMenu.add(companyMenu);
		/** Company menu items list **/
		JMenuItem chooseLocation = 	companyMenu.add(new ChooseLocationAction("Choose Location"));
		chooseLocation.setToolTipText("Allows you to switch between locations.");
	}
	private class ChooseLocationAction extends AbstractAction
	{
		public ChooseLocationAction(String name)
		{
			super(name);
		}
		@Override public void actionPerformed(ActionEvent event)
		{
			CompanyFrame.super.getContentPane().setBackground(Color.YELLOW);
		}
	}
}