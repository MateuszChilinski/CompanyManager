package location;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import company.CompanyView;
import item.Item;
import item.ItemController;
import item.ItemView;

public class LocationView {
	
	LocationController controller;
	private JPanel toolbarPanel = new JPanel();
	private JPanel locationEditorPanel = new JPanel(new GridBagLayout());
	private JTextField searchBar = new JTextField("");
	private DefaultTableModel itemTableModel = new ItemList();
	private UpdateSearch updateList;
	private JTable itemTable;
	LocationEditor locationDialbox;
	public void printLocationInfo(String name, int itemsCount)
	{
		System.out.printf("Location name: %s\nItems in stock: %d\n", name, itemsCount);
	}
	public void setController(LocationController newController)
	{
		this.controller = newController;
	}
	public void printLocationItemsInfo(ArrayList<Item> items, String name)
	{
		System.out.printf("Location name: %s\n", name);
		for(Item currentItem : items)
		{
			ItemController currentItemController = new ItemController(currentItem, new ItemView());
			currentItemController.printInfo();
		}
	}
	
	public void displayDialbox(JFrame owner, boolean isNew) {
		locationDialbox = new LocationEditor(owner, isNew);
	}
	public GridBagConstraints setPosition(int gridx, int gridy, double weightx, double weighty)
	{
		GridBagConstraints position = new GridBagConstraints();
		position.anchor = GridBagConstraints.NORTHWEST;
		position.fill = GridBagConstraints.HORIZONTAL;
		position.gridx = gridx;
		position.gridy = gridy;
		position.weightx = weightx;
		position.weighty = weighty;
		return position;
	}
	public GridBagConstraints setPosition(int gridx, int gridy, double weightx, double weighty, Insets insets)
	{
		GridBagConstraints position = setPosition(gridx, gridy, weightx, weighty);
		position.insets = insets;
		return position;
	}
	public JPanel locationEditor(JFrame owner, JDialog dialogBox, boolean isNew)
	{
		locationEditorPanel.setSize(1000, 1000);
		initiateMainPanel(dialogBox, isNew);
		initiateToolbar();
		return locationEditorPanel;
	}
	private void initiateMainPanel(JDialog dialogBox, boolean isNew) {
		
		JPanel locationNamePanel = new JPanel(new GridBagLayout());
		/** Location name **/
		JTextField locationName = new JTextField(controller.getName());
		locationNamePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Name"));
		locationNamePanel.add(locationName, setPosition(0, 0, 1, 0));
		locationEditorPanel.add(locationNamePanel, setPosition(1, 0, 0, 0,  new Insets(5, 5, 3, 3)));
		/** Items in location **/
		printItems(controller.getItems(""));
		/** Panel for save & cancel **/
		JPanel actionPanel = new JPanel();
		/** Saving button **/
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(event -> { dialogBox.setVisible(false); saveLocation(locationName.getText());});
		actionPanel.add(saveButton, setPosition(0, 1, 1, 0));
		/** Cancel button **/
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(event -> { dialogBox.setVisible(false); if(isNew == true) controller.removeLocation(); });
		actionPanel.add(cancelButton, setPosition(0, 1, 1, 0));
		/** Adding action panel to location editor **/
		locationEditorPanel.add(actionPanel, setPosition(1, 2, 1, 0));
	}
	public void printItems(ArrayList<Item> locationItems)
	{
		JPanel itemsPanel = new JPanel(new GridBagLayout());
		itemsPanel.add(searchBar, setPosition(0,0,1,0));
		updateList = new UpdateSearch(searchBar, itemTableModel);
		updateLocations(locationItems);
		searchBar.getDocument().addDocumentListener(updateList);
		//itemTable.addMouseListener(new TableDobuleClick());
		itemsPanel.add(itemTable, setPosition(0,1,1,0));
		itemsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Items"));
		locationEditorPanel.add(itemsPanel, setPosition(1, 1, 0, 0));
	}
	public void initiateToolbar()
	{
		JToolBar helpingToolbar = new JToolBar(JToolBar.VERTICAL);
		helpingToolbar.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Location"));
		helpingToolbar.setFloatable(false);
		JButton addItem = new JButton("Add an item");
		addItem.addActionListener(new AddItem("Add an item"));
		helpingToolbar.add(addItem);
		toolbarPanel.add(helpingToolbar, setPosition(0, 0, 0, 0, new Insets(5, 5, 3, 3)));
		locationEditorPanel.add(toolbarPanel, setPosition(0, 0, 0, 0, new Insets(5, 5, 3, 3)));
	}
	public void updateLocations(ArrayList<Item> companyLocations)
	{
		updateList.updateList();
		itemTable = new JTable(itemTableModel);
	}
	
	public void saveLocation(String name)
	{
		controller.editLocation(name);
	}
	private class ItemList extends DefaultTableModel
	{
		ItemList()
		{
			this.addColumn("No.");
			this.addColumn("Location name");
			this.addColumn("Items count");
			this.addColumn("Item price");
			this.addRow(new Object[]{"No.", "Location name", "Items count", "Item price"});
		}
		@Override public boolean isCellEditable(int row, int column)
		{
			if((column == 1 || column == 3) && row != 0)
				return true;
			return false;
		}
	}
	private class AddItem extends AbstractAction
	{
		public AddItem(String name)
		{
			super(name);
		}
		@Override public void actionPerformed(ActionEvent event)
		{
			Item newItem = controller.addItem("z", 1, 1, 2.0);
			controller.itemEditor((JDialog) LocationView.this.locationDialbox, newItem, true);
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
			tableModel.addRow(new Object[]{"<html><b>No.</b></html>", "<html><b>Location name</b></html>", "<html><b>Items count</b></html>", "<html><b>Item price</b></html>"});
			ArrayList<Item> currentList = controller.getItems(currentString);
			for(Item currentItem : currentList)
			{
				tableModel.addRow(new Object[]{i++, currentItem.getName(), currentItem.getQuantity(), currentItem.getPrice()});
			}
		}
	}
	private class LocationEditor extends JDialog
	{
		LocationEditor(JFrame owner, boolean isNew)
		{
			super(owner, "Location Editor", true);
			super.addWindowListener(new WindowAdapter() { public void windowClosing(WindowEvent e) { controller.removeLocation(); } });
			this.setResizable(false);
			add(locationEditor(owner, this, isNew));
			pack();
			setVisible(true);
		}
	}
}
