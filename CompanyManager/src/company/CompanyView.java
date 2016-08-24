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
	private DefaultTableModel locationModel = new LocationList();;
	private UpdateSearch updateList;
	CompanyController controller;
	JTable locationTable;
	JTextField searchBar = new JTextField("");
	public CompanyView()
	{
		initWindow();
		initMainMenu();
		initPanels();
	}
	public void setController(CompanyController companyController) {
		this.controller = companyController;
		updateTitle();
	}
	public void updateTitle()
	{
		setTitle("Company Manager - " + controller.getName());
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
		/** Constructing help menu **/
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setToolTipText("In case you need some help.");
		mainMenu.add(helpMenu);
		JMenuItem about = helpMenu.add(new AboutAction("About"));
		createNewLocation.setToolTipText("About software and author.");
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
		helpingToolbar.add(new CreateNewLocationAction("Add location"));
		helpingToolbar.add(new ChangeCompanyNameAction("Change name"));
		toolbarPanel.add(helpingToolbar, position);
	}
	public void printLocations(ArrayList<Location> companyLocations)
	{
		GridBagConstraints position = new GridBagConstraints();
		position.anchor = GridBagConstraints.NORTHWEST;
		position.fill = GridBagConstraints.HORIZONTAL;
		position.weightx = 1;
		position.gridx = 0;
		position.gridy = 0;
		contentPanel.add(searchBar, position);
		position.gridx = 0;
		position.gridy = 1;
		updateList = new UpdateSearch(searchBar, locationModel);
		updateLocations(companyLocations);
		searchBar.getDocument().addDocumentListener(updateList);
		locationTable.addMouseListener(new TableDobuleClick());
		contentPanel.add(locationTable, position);
	}
	public void updateLocations(ArrayList<Location> companyLocations)
	{
		updateList.updateList();
		locationTable = new JTable(locationModel);
	}
	public void addLocation(String name)
	{
		locationModel.addRow(new Object[] {locationModel.getRowCount(), name, 0});
	}
	public void locationEditor(JFrame owner, Location location)
	{
		
	}
	private class TableDobuleClick extends MouseAdapter
	{
		@Override public void mousePressed(MouseEvent me) 
		{ 
			JTable table = (JTable) me.getSource();
	        Point p = me.getPoint();
	        int locationID = table.rowAtPoint(p)-1;
	        int column = table.columnAtPoint(p);
	        if (me.getClickCount() == 2 && column != 1) {
	            controller.locationEditor(CompanyView.this, controller.getLocation(locationID));
	        }
		}
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
		DefaultTableModel tableModel;
		ArrayList<Location> list;
		UpdateSearch(JTextField searchBar, DefaultTableModel tableModel)
		{
			this.tableModel = tableModel;
			this.searchBar = searchBar;
			updateList();
		}
		@Override public void changedUpdate(DocumentEvent e) {
			updateList();
		}
		@Override public void removeUpdate(DocumentEvent e) {
			updateList();
		}
		@Override public void insertUpdate(DocumentEvent e) {
			updateList();
		}
		private void updateList() {
			String currentString = searchBar.getText();
			int i = 1;
			tableModel.setRowCount(0);
			tableModel.addRow(new Object[]{"<html><b>No.</b></html>", "<html><b>Location name</b></html>", "<html><b>Items count</b></html>"});
			ArrayList<Location> currentList = controller.getLocations(currentString);
			for(Location currentLocation : currentList)
			{
				tableModel.addRow(new Object[]{i++, currentLocation.getName(), currentLocation.getItemsCount()});
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
			String s = (String) JOptionPane.showInputDialog(CompanyView.super.getContentPane(), "Type new company name.", "Company Name", JOptionPane.QUESTION_MESSAGE, null, null, controller.getName());
				if ((s != null) && (s.length() > 0)) {
					controller.setName(s);
				}
			updateTitle();
		}
	}
	private class AboutAction extends AbstractAction
	{
		public AboutAction(String name)
		{
			super(name);
		}
		@Override public void actionPerformed(ActionEvent event)
		{
			JOptionPane.showMessageDialog(CompanyView.this, "<html><b>Company Manager</b><br/><br/>Author: Mateusz Chiliñski<br/>Email: mateusz@chilinski.eu<br/><br/></html>");
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
			Location newLocation = new Location("New location");
			controller.locationEditor(CompanyView.this, newLocation);
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
