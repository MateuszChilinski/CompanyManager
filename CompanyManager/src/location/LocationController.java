package location;

import java.util.ArrayList;

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
		this.setName(name);
	}
	public void setName(String name)
	{
		model.setName(name);
	}
	public ArrayList<Item> getItems(String name)
	{
		ArrayList<Item> temp = new ArrayList<Item>();
		for(Item currentLocation : model.getItems())
		{
			if(currentLocation.getName().toLowerCase().contains(name.toLowerCase()))
				temp.add(currentLocation);
		}
		return temp;
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
		
	}
}
