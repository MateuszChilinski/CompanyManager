package location;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import company.CompanyView;
import item.Item;
import item.ItemController;
import item.ItemView;

public class LocationView {
	
	LocationController controller;
	
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
	
	public void displayDialbox(JFrame owner) {
		LocationEditor z = new LocationEditor(owner);
	}
	
	public JPanel locationEditor(JFrame owner, JDialog dialogBox)
	{
		GridBagConstraints position = new GridBagConstraints();
		position.anchor = GridBagConstraints.NORTHWEST;
		position.fill = GridBagConstraints.HORIZONTAL;
		position.weightx = 1;
		position.gridx = 0;
		position.gridy = 0;
		JPanel locationEditorPanel = new JPanel(new GridBagLayout());
		locationEditorPanel.setSize(1000, 1000);
		JTextField locationName = new JTextField(controller.getName());
		locationEditorPanel.add(locationName, position);
		position.gridy = 1;
		position.weightx = 0.1;
		JPanel actionPanel = new JPanel();
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(event -> { dialogBox.setVisible(false); saveLocation(locationName.getText());});
		actionPanel.add(saveButton, position);
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(event -> { dialogBox.setVisible(false); controller.removeLocation(); });
		actionPanel.add(cancelButton, position);
		locationEditorPanel.add(actionPanel, position);
		//locationEditorPanel.setLayout(new GridBagLayout());
		return locationEditorPanel;
	}
	
	public void saveLocation(String name)
	{
		controller.editLocation(name);
	}
	
	private class LocationEditor extends JDialog
	{
		LocationEditor(JFrame owner)
		{
			super(owner, "Location Editor", true);
			add(locationEditor(owner, this));
			pack();
			setVisible(true);
		}
	}
}
