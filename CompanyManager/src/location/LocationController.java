package location;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JDialog;
import javax.swing.JFrame;

import item.Item;
import item.ItemController;
import item.ItemView;

public class LocationController {
	private Location model;
	private LocationView view;
	public LocationController(Location location, LocationView view) {
		this.model = location;
		view.setController(this);
		this.view = view;
	}
	public void removeLocation()
	{
		model.removeLocation();
	}
	public Item addItem(String aItemName, int aQuantity, int aMaximum, double aPrice) throws IllegalArgumentException
	{
		return model.addItem(aItemName, aQuantity, aMaximum, aPrice);
	}
	public void addItem(Item item)
	{
		model.addItem(item);
	}
	public void printLocationInfo()
	{
		view.printLocationInfo(model.getName(), model.getItemsCount());
	}
	public String getName()
	{
		return model.getName();
	}
	public int getItemsCount()
	{
		return model.getItemsCount();
	}
	public void printLocationItemsInfo()
	{
		view.printLocationItemsInfo(model.getItems(), model.getName());
	}
	public void locationEditor(JFrame owner, boolean isNew)
	{
		view.displayDialbox(owner, isNew);
	}
	public void editLocation(String name)
	{
		setName(name);
	}
	public void setName(String name)
	{
		model.setName(name);
	}
	public ArrayList<Item> getItems(String name)
	{
		ArrayList<Item> temp = new ArrayList<Item>();
		int i = 0;
		removeNulls();
		for(Item currentItem : model.getItems())
		{
			if(currentItem.getName().toLowerCase().contains(name.toLowerCase()))
				temp.add(currentItem);
		}
		return temp;
	}
	private void removeNulls() {
		ArrayList<Item> items = model.getItems();
		for(int i = 0; i < model.getItemsCount(); i++)
		{
			if(items.get(i).getName() == null)
			{
				items.remove(i); i--;
			}
		}
	}
	public void itemEditor(JDialog locationDialbox, Item newItem, boolean isNew) {
		ItemView itemView = new ItemView();
		ItemController itemController = new ItemController(newItem, itemView);
		itemView.setController(itemController);
		itemController.itemEditor(locationDialbox, isNew);
		if(newItem.getName() != null && isNew == true)
			this.addItem(newItem);
		updateItems();
	}
	public void updateItems()
	{
		view.updateItems(model.getItems());
	}
	public Item getItem(int itemID) {
		return model.getItem(itemID);
	}
}
