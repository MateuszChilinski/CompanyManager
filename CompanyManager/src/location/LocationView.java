package location;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

import company.CompanyView;
import item.Item;
import item.ItemController;
import item.ItemView;

public class LocationView {
	
	LocationController controller;
	private JPanel toolbarPanel = new JPanel();
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
		LocationEditor z = new LocationEditor(owner, isNew);
	}
	
	public JPanel locationEditor(JFrame owner, JDialog dialogBox, boolean isNew)
	{
		GridBagConstraints position = new GridBagConstraints();
		position.anchor = GridBagConstraints.NORTHWEST;
		position.fill = GridBagConstraints.HORIZONTAL;
		position.weightx = 1;
		position.weighty = 1;
		position.gridx = 1;
		position.gridy = 0;
		JPanel locationEditorPanel = new JPanel(new GridBagLayout());
		locationEditorPanel.setSize(1000, 1000);
		JTextField locationName = new JTextField(controller.getName());
		JPanel locationNamePanel = new JPanel(new GridBagLayout());
		locationNamePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Name"));
		locationNamePanel.add(locationName, position);
		locationEditorPanel.add(locationNamePanel, position);
		position.gridy = 1;
		position.weightx = 1;
		JPanel actionPanel = new JPanel();
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(event -> { dialogBox.setVisible(false); saveLocation(locationName.getText());});
		actionPanel.add(saveButton, position);
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(event -> { dialogBox.setVisible(false); if(isNew == true) controller.removeLocation(); });
		actionPanel.add(cancelButton, position);
		locationEditorPanel.add(actionPanel, position);
		
		JToolBar helpingToolbar = new JToolBar(JToolBar.VERTICAL);
		helpingToolbar.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Location"));
		position.insets = new Insets(5, 5, 3, 3);
		position.gridx = 0;
		position.gridy = 0;
		position.weightx = 0;
		helpingToolbar.setFloatable(false);
		helpingToolbar.add(new JButton("Add an item"));
		toolbarPanel.add(helpingToolbar, position);
		locationEditorPanel.add(toolbarPanel, position);
		//locationEditorPanel.setLayout(new GridBagLayout());
		return locationEditorPanel;
	}
	
	public void saveLocation(String name)
	{
		controller.editLocation(name);
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
