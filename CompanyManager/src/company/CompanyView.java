package company;

import java.util.ArrayList;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import location.Location;
import location.LocationController;
import location.LocationView;

public class CompanyView extends JFrame{
	
	private static final int DEFAULT_WIDTH = 300;
	private static final int DEFAULT_HEIGHT = 200;
	private JPanel mainPanel = new JPanel();
	public CompanyView()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
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
		/** Adding main Panel **/
		add(mainPanel);
	}
	public void printLocations(ArrayList<Location> companyLocations)
	{
		JTextArea companyInfo = new JTextArea();
		companyInfo.setEditable(false);
		for(Location currentLocation : companyLocations)
		{
			LocationController currentLocationController = new LocationController(currentLocation, new LocationView());
			companyInfo.setText(companyInfo.getText() + "Location name: " + currentLocationController.getName() + "\nItems in stock: " + currentLocationController.getItemsCount() + "\n");
		}
		add(companyInfo);
	}
	
	private class ChooseLocationAction extends AbstractAction
	{
		public ChooseLocationAction(String name)
		{
			super(name);
		}
		@Override public void actionPerformed(ActionEvent event)
		{
			CompanyView.super.getContentPane().setBackground(Color.YELLOW);
		}
	}
}
