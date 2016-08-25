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
	private JPanel locationEditorPanel = new JPanel(new GridBagLayout());
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
		JTextField locationName = new JTextField(controller.getName());
		JPanel locationNamePanel = new JPanel(new GridBagLayout());
		locationNamePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Name"));
		locationNamePanel.add(locationName, setPosition(0, 0, 1, 0));
		locationEditorPanel.add(locationNamePanel, setPosition(1, 0, 0, 0));
		JPanel actionPanel = new JPanel();
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(event -> { dialogBox.setVisible(false); saveLocation(locationName.getText());});
		actionPanel.add(saveButton, setPosition(0, 1, 1, 0));
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(event -> { dialogBox.setVisible(false); if(isNew == true) controller.removeLocation(); });
		actionPanel.add(cancelButton, setPosition(0, 1, 1, 0));
		locationEditorPanel.add(actionPanel, setPosition(1, 1, 1, 0));
	}
	public void initiateToolbar()
	{
		JToolBar helpingToolbar = new JToolBar(JToolBar.VERTICAL);
		helpingToolbar.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Location"));
		helpingToolbar.setFloatable(false);
		helpingToolbar.add(new JButton("Add an item"));
		toolbarPanel.add(helpingToolbar, setPosition(0, 0, 0, 0, new Insets(5, 5, 3, 3)));
		locationEditorPanel.add(toolbarPanel, setPosition(0, 0, 0, 0, new Insets(5, 5, 3, 3)));
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
