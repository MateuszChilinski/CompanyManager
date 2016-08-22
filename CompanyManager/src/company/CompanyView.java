package company;

import java.util.ArrayList;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import location.Location;
import location.LocationController;
import location.LocationView;

public class CompanyView extends JFrame{
	
	private static final int DEFAULT_WIDTH = 1000;
	private static final int DEFAULT_HEIGHT = 800;
	private JPanel mainPanel = new JPanel(new GridBagLayout());
	public CompanyView()
	{
		setLayout(new GridBagLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setTitle("Company Manager");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		/** Constructing main menu **/
		JMenuBar mainMenu = new JMenuBar();
		setJMenuBar(mainMenu);
		/** Constructing settings menu **/
		JMenu settingsMenu = new JMenu("Settings");
		settingsMenu.setToolTipText("Settings of current view.");
		mainMenu.add(settingsMenu);
		/** Company menu items list **/
		JMenuItem chooseLocation = 	settingsMenu.add(new ChooseLocationAction("Choose Location"));
		chooseLocation.setToolTipText("Allows you to switch between locations.");
		/** Constructing company management menu **/
		JMenu companyMenu = new JMenu("Company");
		companyMenu.setToolTipText("Managment of the company as the whole.");
		mainMenu.add(companyMenu);
		/** Company menu items list **/
		JMenuItem changeCompanyName = companyMenu.add(new ChangeCompanyNameAction("Change Company Name"));
		changeCompanyName.setToolTipText("Change name of your company.");
		JMenuItem createNewLocation = companyMenu.add(new CreateNewLocationAction("Create New Location"));
		createNewLocation.setToolTipText("Add completly new location to your company.");
		/** Adding main Panel **/
		GridBagConstraints position = new GridBagConstraints();
		position.weighty = 1;
		position.weightx = 1;
		position.anchor = GridBagConstraints.NORTHWEST;
		add(mainPanel, position);
	}
	public void printLocations(ArrayList<Location> companyLocations)
	{
		JTextField searchBar = new JTextField("");
		DefaultTableModel model = new LocationList();
		
		searchBar.getDocument().addDocumentListener(new UpdateSearch(searchBar, model, companyLocations));
		GridBagConstraints position = new GridBagConstraints();
		position.anchor = GridBagConstraints.NORTHWEST;
		position.fill = GridBagConstraints.HORIZONTAL;
		position.gridx = 0;
		position.gridy = 0;
		mainPanel.add(searchBar, position);
		int i = 1;
		for(Location currentLocation : companyLocations)
		{
			LocationController currentLocationController = new LocationController(currentLocation, new LocationView());
			model.addRow(new Object[]{i++, currentLocationController.getName(), currentLocationController.getItemsCount()});
		}
		position.gridx = 0;
		position.gridy = 1;
		JTable locationTable = new JTable(model);
		mainPanel.add(locationTable, position);
	}
	private class LocationList extends DefaultTableModel
	{
		LocationList()
		{
			this.addColumn("No.");
			this.addColumn("Location name");
			this.addColumn("Items count");
			this.addRow(new Object[]{"No.", "Location name", "Items count"});
		}
		@Override public boolean isCellEditable(int row, int column)
		{
			if(column == 1)
				return true;
			return false;
		}
	}
	
	private class UpdateSearch implements DocumentListener {
		JTextField searchBar;
		DefaultTableModel model;
		ArrayList<Location> list;
		UpdateSearch(JTextField searchBar, DefaultTableModel model, ArrayList<Location> list)
		{
			this.model = model;
			this.list = list;
			this.searchBar = searchBar;
		}
		public void changedUpdate(DocumentEvent e) {
			updateList();
		}
		public void removeUpdate(DocumentEvent e) {
			updateList();
		}
		public void insertUpdate(DocumentEvent e) {
			updateList();
		}
		private void updateList() {
			String currentString = searchBar.getText();
			CompanyView.super.setTitle(currentString);
			int i = 1;
			model.setRowCount(0);
			model.addRow(new Object[]{"No.", "Location name", "Items count"});
			for(Location currentLocation : list)
			{
				LocationController currentLocationController = new LocationController(currentLocation, new LocationView());
				if(currentLocationController.getName().contains(currentString))
					model.addRow(new Object[]{i++, currentLocationController.getName(), currentLocationController.getItemsCount()});
			}
		}
	}
	
	private class ChangeCompanyNameAction extends AbstractAction
	{
		public ChangeCompanyNameAction(String name)
		{
			super(name);
		}
		@Override public void actionPerformed(ActionEvent event)
		{
			CompanyView.super.setTitle("Last clicked: " + this.getClass().getName());
		}
	}
	private class CreateNewLocationAction extends AbstractAction
	{
		public CreateNewLocationAction(String name)
		{
			super(name);
		}
		@Override public void actionPerformed(ActionEvent event)
		{
			CompanyView.super.setTitle("Last clicked: " + this.getClass().getName());
		}
	}
	private class ChooseLocationAction extends AbstractAction
	{
		public ChooseLocationAction(String name)
		{
			super(name);
		}
		@Override public void actionPerformed(ActionEvent event)
		{
			CompanyView.super.setTitle("Last clicked: " + this.getClass().getName());
		}
	}
}
