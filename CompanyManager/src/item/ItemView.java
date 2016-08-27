package item;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

import location.LocationController;
import location.LocationView;

public class ItemView{
	private JPanel toolbarPanel = new JPanel();
	private JPanel itemEditorPanel = new JPanel(new GridBagLayout());
	ItemController controller;
	public void setController(ItemController newController)
	{
		this.controller = newController;
	}
	public void printInfo(String itemName, int quantity, int maximum, double price)
	{
		System.out.printf("Item name: %s\nQuantity: %d\nMaximum quantity: %d\nPrice per unit: %f", itemName, quantity, maximum, price);
	}
	public JPanel itemEditor(JDialog locationDialbox, JDialog dialogBox, boolean isNew)
	{
		//itemEditorPanel.setSize(1000, 1000);
		initiateMainPanel(dialogBox, isNew);
		initiateToolbar();
		return itemEditorPanel;
	}
	private void initiateToolbar() {
		JToolBar helpingToolbar = new JToolBar(JToolBar.VERTICAL);
		helpingToolbar.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Item"));
		helpingToolbar.setFloatable(false);
		JButton addItem = new JButton("No idea what to put there lol");
		//addItem.addActionListener(new AddItem("Add an item"));
		helpingToolbar.add(addItem);
		toolbarPanel.add(helpingToolbar, setPosition(0, 0, 0, 0, new Insets(5, 5, 3, 3)));
		itemEditorPanel.add(toolbarPanel, setPosition(0, 0, 0, 0, new Insets(5, 5, 3, 3)));
	}
	private void initiateMainPanel(JDialog dialogBox, boolean isNew) {
		JPanel locationNamePanel = new JPanel(new GridBagLayout());
		/** Location name **/
		JTextField locationName = new JTextField(controller.getName());
		locationNamePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Name"));
		locationNamePanel.add(locationName, setPosition(0, 0, 1, 0));
		itemEditorPanel.add(locationNamePanel, setPosition(1, 0, 0, 0,  new Insets(5, 5, 3, 3)));
		/** Info about item **/

		/** Panel for save & cancel **/
		JPanel actionPanel = new JPanel();
		/** Saving button **/
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(event -> { dialogBox.setVisible(false); saveItem(locationName.getText());});
		actionPanel.add(saveButton, setPosition(0, 1, 1, 0));
		/** Cancel button **/
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(event -> { dialogBox.setVisible(false); if(isNew == true) controller.removeItem(); });
		actionPanel.add(cancelButton, setPosition(0, 1, 1, 0));
		/** Adding action panel to location editor **/
		itemEditorPanel.add(actionPanel, setPosition(1, 2, 1, 0));
		
	}
	private void saveItem(String text) {
		// TODO Auto-generated method stub
		
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
	public void displayDialbox(JDialog locationDialbox, boolean isNew) {
		ItemEditor itemEditor = new ItemEditor(locationDialbox, isNew);
	}
	private class ItemEditor extends JDialog
	{
		ItemEditor(JDialog locationDialbox, boolean isNew)
		{
			super(locationDialbox, "Item Editor", true);
			super.addWindowListener(new WindowAdapter() { public void windowClosing(WindowEvent e) { setVisible(false); controller.removeItem(); } });
			this.setResizable(false);
			add(itemEditor(locationDialbox, this, isNew));
			pack();
			setVisible(true);
		}
	}
}
