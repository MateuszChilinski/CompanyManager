package company;

import java.util.ArrayList;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
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
	private JPanel toolbarPanel = new JPanel();
	private JPanel contentPanel = new JPanel(new GridBagLayout());
	private DefaultTableModel locationModel;
	CompanyController controller;
	public CompanyView()
	{
		initWindow();
		initMainMenu();
		initPanels();
	}
	public void setController(CompanyController companyController) {
		this.controller = companyController;
	}
	public void initMainPanel()
	{
		/** Adding main Panel **/
		GridBagConstraints position = new GridBagConstraints();
		position.weighty = 1;
		position.weightx = 1;
		position.anchor = GridBagConstraints.NORTHWEST;
		position.fill = GridBagConstraints.HORIZONTAL;
		add(mainPanel, position);
	}
	public void initWindow()
	{
		setLayout(new GridBagLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setTitle("Company Manager");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	public void initPanels()
	{
		GridBagConstraints position = new GridBagConstraints();
		position.anchor = GridBagConstraints.NORTHWEST;
		position.fill = GridBagConstraints.HORIZONTAL;
		position.weightx = 0;
		position.gridx = 0;
		position.gridy = 0;
		position.insets = new Insets(5, 5, 3, 3);
		initHelpingMenu();
		initMainPanel();
		toolbarPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Company"));
		mainPanel.add(toolbarPanel, position);
		position.gridx = 1;
		position.weightx = 1;
		mainPanel.add(contentPanel, position);
	}
	public void initMainMenu()
	{
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
	}
	public void initHelpingMenu()
	{
		JToolBar helpingToolbar = new JToolBar(JToolBar.VERTICAL);
		GridBagConstraints position = new GridBagConstraints();
		position.anchor = GridBagConstraints.NORTHWEST;
		position.fill = GridBagConstraints.HORIZONTAL;
		position.weightx = 0;
		position.gridx = 0;
		position.gridy = 0;
		helpingToolbar.setFloatable(false);
		JButton addLocation = helpingToolbar.add(new CreateNewLocationAction("Add location"));
		JButton addLocation2 = helpingToolbar.add(new ChooseLocationAction("something idk"));
		toolbarPanel.add(helpingToolbar, position);
	}
	public void printLocations(ArrayList<Location> companyLocations)
	{
		JTextField searchBar = new JTextField("");
		locationModel = new LocationList();
		GridBagConstraints position = new GridBagConstraints();
		position.anchor = GridBagConstraints.NORTHWEST;
		position.fill = GridBagConstraints.HORIZONTAL;
		position.weightx = 1;
		searchBar.getDocument().addDocumentListener(new UpdateSearch(searchBar, locationModel, companyLocations));
		position.gridx = 0;
		position.gridy = 0;
		contentPanel.add(searchBar, position);
		position.gridx = 0;
		position.gridy = 1;
		JTable locationTable = new JTable(locationModel);
		contentPanel.add(locationTable, position);
	}
	public void addLocation(String name)
	{
		locationModel.addRow(new Object[] {locationModel.getRowCount(), name, 0});
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
			if(column == 1 && row != 0)
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
			updateList();
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
			int i = 1;
			model.setRowCount(0);
			model.addRow(new Object[]{"No.", "Location name", "Items count"});
			for(Location currentLocation : list)
			{
				LocationController currentLocationController = new LocationController(currentLocation, new LocationView());
				if(currentLocationController.getName().toLowerCase().contains(currentString.toLowerCase()))
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
			controller.addLocation("zz");
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
