package item;

/*
 * Copyright (c) 2016 Mateusz Chiliñski https://chilinski.eu. All rights reserved.
 * 
 */

import java.awt.event.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;


public class ItemView {
	private JPanel toolbarPanel = new JPanel();
	private JPanel itemEditorPanel = new JPanel(new GridBagLayout());
	ItemController controller;
	
	public void setController(ItemController newController) {
		controller = newController;
	}
	public void printInfo(String itemName, int quantity, int maximum, double price) {
		System.out.printf("Item name: %s\nQuantity: %d\nMaximum quantity: %d\nPrice per unit: %f", itemName, quantity, maximum, price);
	}
	public JPanel itemEditor(JDialog locationDialbox, JDialog dialogBox, boolean isNew) {
		initiateMainPanel(dialogBox, isNew);
		if(isNew == false) {
			initiateToolbar(locationDialbox, dialogBox);
		}
		return itemEditorPanel;
	}
	private void initiateToolbar(JDialog locationDialbox, JDialog dialogBox) {
		JToolBar helpingToolbar = new JToolBar(JToolBar.VERTICAL);
		helpingToolbar.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Item"));
		helpingToolbar.setFloatable(false);
		JButton removeItem = new JButton("Remove item");
		removeItem.addActionListener(new RemoveItem("Remove an item", locationDialbox, dialogBox));
		helpingToolbar.add(removeItem);
		toolbarPanel.add(helpingToolbar, setPosition(0, 0, 0, 0, new Insets(5, 5, 3, 3)));
		itemEditorPanel.add(toolbarPanel, setPosition(0, 0, 0, 0, new Insets(5, 5, 3, 3)));
	}
	private void initiateMainPanel(JDialog dialogBox, boolean isNew) {
		JPanel itemPropertiesPanel = new JPanel(new GridBagLayout());
		/** Item properties **/
		itemPropertiesPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Item propeties"));
		itemEditorPanel.add(itemPropertiesPanel, setPosition(1, 0, 0, 0,  new Insets(5, 5, 3, 3)));
		JTextField itemName = new JTextField(controller.getName());
		itemPropertiesPanel.add(itemName, setPosition(0, 0, 1, 0));
		JTextField itemQuantity = new JTextField(String.valueOf(controller.getQuantity()));
		itemPropertiesPanel.add(itemQuantity, setPosition(0, 1, 1, 0));
		JTextField itemMaximum = new JTextField(String.valueOf(controller.getMaximum()));
		itemPropertiesPanel.add(itemMaximum, setPosition(0, 2, 1, 0));
		JTextField itemPrice = new JTextField(String.valueOf(controller.getPrice()));
		itemPropertiesPanel.add(itemPrice, setPosition(0, 3, 1, 0));
		/** Panel for save & cancel **/
		JPanel actionPanel = new JPanel();
		/** Saving button **/
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(event -> { 
			if(Integer.parseInt(itemQuantity.getText()) > 0 && Integer.parseInt(itemMaximum.getText()) > 0 && Double.parseDouble(itemPrice.getText()) > 0)
			{
				dialogBox.setVisible(false);
				saveItem(itemName.getText(), Integer.parseInt(itemQuantity.getText()), Integer.parseInt(itemMaximum.getText()), Double.parseDouble(itemPrice.getText()));
			}
			else
				JOptionPane.showMessageDialog(dialogBox, "Quantity, maximum quantity and price must be more or equal to zero!", "Error!", JOptionPane.ERROR_MESSAGE);
		});
		actionPanel.add(saveButton, setPosition(0, 1, 1, 0));
		/** Cancel button **/
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(event -> { 
			dialogBox.setVisible(false); 
			if(isNew == true) 
				controller.removeItem(); 
		});
		actionPanel.add(cancelButton, setPosition(0, 1, 1, 0));
		/** Adding action panel to location editor **/
		itemEditorPanel.add(actionPanel, setPosition(1, 2, 1, 0));
		
	}
	private void saveItem(String name, int quantity, int maximum, double price) {
		controller.setName(name);
		controller.setQuantity(quantity);
		controller.setMaximum(maximum);
		controller.setPrice(price);
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
	public void displayDialbox(JDialog locationDialbox, boolean isNew) {
		ItemEditor itemEditor = new ItemEditor(locationDialbox, isNew);
	}
	private class ItemEditor extends JDialog {
		ItemEditor(JDialog locationDialbox, boolean isNew) {
			super(locationDialbox, "Item Editor", true);
			addWindowListener(new WindowAdapter() { public void windowClosing(WindowEvent e) { setVisible(false); controller.removeItem(); } });
			setResizable(false);
			add(itemEditor(locationDialbox, this, isNew));
			pack();
			setVisible(true);
		}
	}
	private class RemoveItem extends AbstractAction {
		JDialog dialogBox;
		JDialog locationDialbox;
		
		public RemoveItem(String name, JDialog aLocationDialbox, JDialog aDialogBox) {
			super(name);
			dialogBox = aDialogBox;
			locationDialbox = aLocationDialbox;
		}
		@Override 
		public void actionPerformed(ActionEvent event) {			
			controller.removeItem();
			dialogBox.setVisible(false);
		}
	}
}
