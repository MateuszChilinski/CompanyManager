package company;

import java.util.ArrayList;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
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
	private CompanyController controller;
	private DefaultTableModel locationTableModel = new LocationList();;
	private UpdateSearch updateList;
	private JTable locationTable;
	private JTextField searchBar = new JTextField("");
	public CompanyView() {
		initWindow();
		initMainMenu();
		initPanels();
	}
	public void setController(CompanyController companyController) {
		controller = companyController;
		updateTitle();
	}
	public void initWindow() {
		setLayout(new GridBagLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	public void initMainPanel() {
		add(mainPanel, setPosition(0, 0, 1, 1));
	}
	public void initPanels() {
		initHelpingMenu();
		initMainPanel();
		toolbarPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Company"));
		mainPanel.add(toolbarPanel, setPosition(0, 0, 0, 0, new Insets(5, 5, 3, 3)));
		mainPanel.add(contentPanel, setPosition(1, 0, 1, 1));
	}
	public void initMainMenu() {
		/** Constructing main menu **/
		JMenuBar mainMenu = new JMenuBar();
		setJMenuBar(mainMenu);
		/** Constructing settings menu **/
		JMenu settingsMenu = new JMenu("Settings");
		settingsMenu.setToolTipText("Settings of current view.");
		mainMenu.add(settingsMenu);
		/** Company menu items list **/
		JMenuItem chooseLocation = 	settingsMenu.add(new SaveCompany("Save changes"));
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
	public void initHelpingMenu() {
		JToolBar helpingToolbar = new JToolBar(JToolBar.VERTICAL);
		helpingToolbar.setFloatable(false);
		helpingToolbar.add(new CreateNewLocationAction("Add location"));
		helpingToolbar.add(new ChangeCompanyNameAction("Change name"));
		toolbarPanel.add(helpingToolbar, setPosition(0,0,0,0));
	}
	public void updateTitle() {
		setTitle("Company Manager - " + controller.getName());
	}

	public GridBagConstraints setPosition(int gridx, int gridy, double weightx, double weighty) {
		GridBagConstraints position = new GridBagConstraints();
		position.anchor = GridBagConstraints.NORTHWEST;
		position.fill = GridBagConstraints.HORIZONTAL;
		position.gridx = gridx;
		position.gridy = gridy;
		position.weightx = weightx;
		position.weighty = weighty;
		return position;
	}
	public GridBagConstraints setPosition(int gridx, int gridy, double weightx, double weighty, Insets insets) {
		GridBagConstraints position = setPosition(gridx, gridy, weightx, weighty);
		position.insets = insets;
		return position;
	}
	public void printLocations(ArrayList<Location> companyLocations) {
		contentPanel.add(searchBar, setPosition(0,0,1,0));
		updateList = new UpdateSearch(searchBar, locationTableModel);
		updateLocations(companyLocations);
		searchBar.getDocument().addDocumentListener(updateList);
		locationTable.addMouseListener(new TableDobuleClick());
		contentPanel.add(locationTable, setPosition(0,1,1,0));
		locationTableModel.addTableModelListener(new TableModelListener(){
			public void tableChanged(TableModelEvent e) {
		         int column = e.getColumn();
		         int row = e.getFirstRow();
		         if(column == 1)
		         {
		        	 controller.getLocation(row-1).setName((String) locationTableModel.getValueAt(row, column));
		         }
		      }
		});
	}
	public void updateLocations(ArrayList<Location> companyLocations) {
		updateList.updateList();
		locationTable = new JTable(locationTableModel);
	}
	public void addLocation(String name) {
		locationTableModel.addRow(new Object[] {locationTableModel.getRowCount(), name, 0});
	}
	private class TableDobuleClick extends MouseAdapter {
		@Override 
		public void mousePressed(MouseEvent me) { 
			JTable table = (JTable) me.getSource();
	        Point p = me.getPoint();
	        int locationID = table.rowAtPoint(p)-1;
	        int column = table.columnAtPoint(p);
	        if (me.getClickCount() == 2 && column != 1 && locationID != -1) {
	            controller.locationEditor(CompanyView.this, controller.getLocation(locationID), false);
	        }
		}
	}
	private class LocationList extends DefaultTableModel {
		LocationList() {
			addColumn("No.");
			addColumn("Location name");
			addColumn("Items count");
			addRow(new Object[]{"No.", "Location name", "Items count"});
		}
		@Override 
		public boolean isCellEditable(int row, int column) {
			if(column == 1 && row != 0)
				return true;
			return false;
		}
	}
	private class UpdateSearch implements DocumentListener {
		JTextField searchBar;
		DefaultTableModel tableModel;
		ArrayList<Location> list;
		UpdateSearch(JTextField aSearchBar, DefaultTableModel aTableModel) {
			tableModel = aTableModel;
			searchBar = aSearchBar;
			updateList();
		}
		@Override 
		public void changedUpdate(DocumentEvent e) {
			updateList();
		}
		@Override 
		public void removeUpdate(DocumentEvent e) {
			updateList();
		}
		@Override
		public void insertUpdate(DocumentEvent e) {
			updateList();
		}
		private void updateList() {
			String currentString = searchBar.getText();
			int i = 1;
			tableModel.setRowCount(0);
			tableModel.addRow(new Object[]{"<html><b>No.</b></html>", "<html><b>Location name</b></html>", "<html><b>Items count</b></html>"});
			ArrayList<Location> currentList = controller.getLocations(currentString);
			for(Location currentLocation : currentList) {
				tableModel.addRow(new Object[]{i++, currentLocation.getName(), currentLocation.getItemsCount()});
			}
		}
	}
	
	private class SaveCompany extends AbstractAction {
		public SaveCompany(String name) {
			super(name);
		}
		@Override 
		public void actionPerformed(ActionEvent event) {
			try {
				controller.saveData();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private class ChangeCompanyNameAction extends AbstractAction {
		public ChangeCompanyNameAction(String name) {
			super(name);
		}
		@Override 
		public void actionPerformed(ActionEvent event) {
			String s = (String) JOptionPane.showInputDialog(getContentPane(), "Type new company name.", "Company Name", JOptionPane.QUESTION_MESSAGE, null, null, controller.getName());
				if ((s != null) && (s.length() > 0)) {
					controller.setName(s);
				}
			updateTitle();
		}
	}
	private class AboutAction extends AbstractAction {
		public AboutAction(String name) {
			super(name);
		}
		@Override 
		public void actionPerformed(ActionEvent event) {
			JOptionPane.showMessageDialog(CompanyView.this, "<html><b>Company Manager</b><br/><br/>Author: Mateusz Chiliñski<br/>Email: mateusz@chilinski.eu<br/><br/></html>");
		}
	}
	private class CreateNewLocationAction extends AbstractAction {
		public CreateNewLocationAction(String name) {
			super(name);
		}
		@Override 
		public void actionPerformed(ActionEvent event) {
			Location newLocation = new Location("New location");
			controller.locationEditor(CompanyView.this, newLocation, true);
		}
	}
	private class ChooseLocationAction extends AbstractAction {
		public ChooseLocationAction(String name) {
			super(name);
		}
		@Override 
		public void actionPerformed(ActionEvent event) {
			setTitle("Last clicked: " + this.getClass().getName());
		}
	}
}
